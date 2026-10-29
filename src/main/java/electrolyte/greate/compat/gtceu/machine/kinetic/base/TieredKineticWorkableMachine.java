package electrolyte.greate.compat.gtceu.machine.kinetic.base;

import com.gregtechceu.gtceu.api.blockentity.BlockEntityCreationInfo;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait;
import com.gregtechceu.gtceu.api.machine.trait.recipe.IRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeHandlerList;
import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.sync_system.annotations.SaveField;
import com.gregtechceu.gtceu.api.sync_system.annotations.SyncToClient;
import com.gregtechceu.gtceu.common.machine.trait.CleanroomReceiverTrait;
import com.gregtechceu.gtceu.utils.ISubscription;
import electrolyte.greate.compat.gtceu.machine.kinetic.KineticMachineRecipeLogic;
import electrolyte.greate.compat.gtceu.machine.kinetic.StressRecipeHandler;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TieredKineticWorkableMachine extends TieredKineticMachine implements IRecipeLogicMachine, IOverclockMachine {

    @Getter
    @SaveField
    @SyncToClient
    public final RecipeLogic recipeLogic;
    @Getter
    public final GTRecipeType[] recipeTypes;
    @Getter
    @Setter
    @SaveField
    public int activeRecipeType;

    @Getter
    @SaveField
    protected int overclockTier;

    @Getter
    protected final CleanroomReceiverTrait cleanroomReceiverTrait;

    @Getter
    protected final Map<IO, List<RecipeHandlerList>> capabilitiesProxy;
    @Getter
    protected final Map<IO, Map<RecipeCapability<?>, List<IRecipeHandler<?>>>> capabilitiesFlat;
    protected final List<ISubscription> traitSubscriptions;

    private double stressConversionRate = 1.0;

    private TieredKineticWorkableMachine(BlockEntityCreationInfo info, RecipeLogic recipeLogic, IO io, TieredKineticBlockEntity kineticBE) {
        super(info, io, kineticBE);
        this.recipeTypes = this.getDefinition().getRecipeTypes();
        this.activeRecipeType = 0;
        this.overclockTier = getMaxOverclockTier();
        this.cleanroomReceiverTrait = attachTrait(new CleanroomReceiverTrait());
        this.recipeLogic = attachTrait(recipeLogic);
        this.capabilitiesProxy = new EnumMap<>(IO.class);
        this.capabilitiesFlat = new EnumMap<>(IO.class);
        this.traitSubscriptions = new ArrayList<>();
    }

    public TieredKineticWorkableMachine(BlockEntityCreationInfo info, IO io, TieredKineticBlockEntity kineticBE) {
        this(info, new KineticMachineRecipeLogic(), io, kineticBE);
    }

    public TieredKineticWorkableMachine(BlockEntityCreationInfo info, double stressConversionRate, TieredKineticBlockEntity kineticBE) {
        this(info, IO.IN, kineticBE);
        this.stressConversionRate = stressConversionRate;
    }

    public TieredKineticWorkableMachine(BlockEntityCreationInfo info, TieredKineticBlockEntity kineticBE) {
        this(info, IO.IN, kineticBE);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.addHandlerList(RecipeHandlerList.of(IO.IN, new StressRecipeHandler(stressContainer, stressConversionRate)));

        Map<IO, List<IRecipeHandler<?>>> ioTraits = new EnumMap<>(IO.class);
        for(MachineTrait trait : getAllTraits()) {
            if(trait instanceof IRecipeHandlerTrait<?> handlerTrait) {
                ioTraits.computeIfAbsent(handlerTrait.getHandlerIO(), (i) -> new ArrayList<>()).add(handlerTrait);
            }
        }

        for(Map.Entry<IO, List<IRecipeHandler<?>>> entry : ioTraits.entrySet()) {
            RecipeHandlerList handlerList = RecipeHandlerList.of(entry.getKey(), entry.getValue());
            this.addHandlerList(handlerList);
            this.traitSubscriptions.add(handlerList.subscribe(recipeLogic::updateTickSubscription));
        }
    }

    @Override
    public void onUnload() {
        super.onUnload();
        this.traitSubscriptions.forEach(ISubscription::unsubscribe);
        this.traitSubscriptions.clear();
        this.capabilitiesProxy.clear();
        this.capabilitiesFlat.clear();
    }

    @Override
    public @NotNull GTRecipeType getRecipeType() {
        return recipeTypes[activeRecipeType];
    }

    @Override
    public void clientTick() {
        super.clientTick();
        if(this.recipeLogic != null) {
            this.recipeLogic.updateSound();
        }
    }

    @Override
    public void setOverclockTier(int tier) {
        if(!isRemote() && tier >= getMinOverclockTier() && tier <= getMaxOverclockTier()) {
            this.overclockTier = tier;
            this.recipeLogic.markLastRecipeDirty();
        }
    }

    @Override
    public int getMaxOverclockTier() {
        return getTier();
    }

    @Override
    public int getMinOverclockTier() {
        return 0;
    }

    public void cycleActiveRecipeType() {
        int newActiveRecipeType = (getActiveRecipeType() + 1) % recipeTypes.length;
        setActiveRecipeType(newActiveRecipeType);
        getRecipeLogic().updateTickSubscription();
    }
}

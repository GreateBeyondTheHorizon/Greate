package electrolyte.greate.content.kinetics.press;

import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

public class TieredPressingBehaviour extends PressingBehaviour {
    public <T extends SmartBlockEntity & PressingBehaviourSpecifics> TieredPressingBehaviour(T be) {
        super(be);
        whileItemHeld((s, i) -> TieredBeltPressingCallbacks.whenItemHeld(s, i, this));
    }
}

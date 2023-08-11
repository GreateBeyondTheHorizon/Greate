package electrolyte.greate.content.kinetics.simpleRelays;

import com.jozufozu.flywheel.core.PartialModel;

public interface ITieredEncasedCogwheel {

    PartialModel getHalfShaft();

    PartialModel getCogwheelModel();
}

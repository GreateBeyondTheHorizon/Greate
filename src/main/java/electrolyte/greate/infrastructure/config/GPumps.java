package electrolyte.greate.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class GPumps extends ConfigBase {

	public final ConfigGroup pressure = group(1, "pressure", Comments.pressure);
	public final ConfigFloat andesitePressure = f(4, 0, "andesitePressure");
	public final ConfigFloat steelPressure = f(16, 0, "steelPressure");
	public final ConfigFloat aluminiumPressure = f(64, 0, "aluminiumPressure");
	public final ConfigFloat stainlessSteelPressure = f(256, 0, "stainlessSteelPressure");
	public final ConfigFloat titaniumPressure = f(1028, 0, "titaniumPressure");
	public final ConfigFloat tungstensteelPressure = f(4096, 0, "tungstensteelPressure");
	public final ConfigFloat palladiumPressure = f(16384, 0, "palladiumPressure");
	public final ConfigFloat naquadahPressure = f(65536, 0, "naquadahPressure");
	public final ConfigFloat darmstadtiumPressure = f(262144, 0, "darmstadtiumPressure");
	public final ConfigFloat neutroniumPressure = f(1048576, 0, "neutroniumPressure");

	@Override
	public String getName() {
		return "pumps";
	}

	private static class Comments {
		static String pressure = "Configure the individual pressure of pumps. Note that pressure is multiplied by the speed of the pump";
	}
}

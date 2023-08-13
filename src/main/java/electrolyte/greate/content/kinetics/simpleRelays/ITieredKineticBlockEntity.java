package electrolyte.greate.content.kinetics.simpleRelays;

public interface ITieredKineticBlockEntity {

    double getMaxCapacity();

    void updateFromNetwork(float maxStress, float currentStress, int networkSize, double networkMaxCapacity);
}

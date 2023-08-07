package electrolyte.greate.content.kinetics.simpleRelays;

public interface ITieredKineticBlockEntity {

    double getShaftMaxCapacity();

    void updateFromNetwork(float maxStress, float currentStress, int networkSize, double networkMaxCapacity);
}

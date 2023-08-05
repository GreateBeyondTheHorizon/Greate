package electrolyte.greate.be;

public interface ITieredKineticBlockEntity {

    float getShaftMaxCapacity();

    void updateFromNetwork(float maxStress, float currentStress, int networkSize, float networkMaxCapacity);
}

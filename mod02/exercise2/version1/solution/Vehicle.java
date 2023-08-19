public class Vehicle {

  /** The current load of the vehicle in kilograms. */
  public double load;
  /** The maximum load of the vehicle in kilograms. */
  public double maxLoad;

  /**
   * This is the basic constructor for all Vehicles.
   *
   * @param max_load   the maximum load of this vehicle in kilograms
   */
  public Vehicle(double max_Load) {
    load = 0.0;
    maxLoad = max_Load;
  }

  /** This method returns the current load in kilograms. */
  public double getLoad() {
    return load;
  }
  /** This method returns the maximum load in kilograms. */
  public double getMaxLoad() {
    return maxLoad;
  }

}

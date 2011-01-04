package genetic.util;

import java.util.Observable;

/**
 * ObservableProperty allows a class to have a property acting as an Observable
 * and then, to use this property to comunicate with Observers. Usefull when a
 * class cannot extend Observable directly.
 * @author romulo
 */
public class ObservableProperty extends Observable {
    /**
     * Defines this Observable instance as changed
     */
    @Override
    public void setChanged() {
        super.setChanged();
    }
}

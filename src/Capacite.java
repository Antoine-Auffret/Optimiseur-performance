public enum Capacite {
    VIDE(0),
    OK(1),
    DANGER(2),
    MAX(3);

    private int value;

    // getter method
    public int getValue()
    {
        return this.value;
    }

    // enum constructor - cannot be public or protected
    Capacite (int value)
    {
        this.value = Math.round((value*Conf.maxBufferSize)/3);
    }
}

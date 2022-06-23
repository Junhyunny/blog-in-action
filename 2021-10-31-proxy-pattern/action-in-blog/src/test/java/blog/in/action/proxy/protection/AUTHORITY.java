package blog.in.action.proxy.protection;

public enum AUTHORITY {

    ADMIN(0),
    NORMAL(1);

    private int accessLevel;

    AUTHORITY(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean accessible(AUTHORITY authority) {
        return (this.accessLevel - authority.accessLevel) >= 0;
    }
}

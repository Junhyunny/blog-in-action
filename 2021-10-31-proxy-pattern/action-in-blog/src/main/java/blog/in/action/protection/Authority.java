package blog.in.action.protection;

public enum Authority {

    ADMIN(0),
    NORMAL(1);

    private final int accessLevel;

    Authority(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean accessible(Authority authority) {
        return (this.accessLevel - authority.accessLevel) >= 0;
    }
}

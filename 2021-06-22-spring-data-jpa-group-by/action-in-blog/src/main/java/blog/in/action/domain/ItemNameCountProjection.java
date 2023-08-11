package blog.in.action.domain;

public interface ItemNameCountProjection {
    long getACount();

    long getBCount();

    long getCCount();

    long getDCount();

    default String string() {
        return String.format(
                "ItemNameCountProjection(%s, %s, %s, %s)",
                this.getACount(),
                this.getBCount(),
                this.getCCount(),
                this.getDCount()
        );
    }
}

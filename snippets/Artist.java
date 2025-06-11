public final class Artist {
    private final String name;
    private final int startYear;
    private final int endYear;

    public Artist(String name, int startYear, int endYear) {
        this.name = name;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    // Getters (no setters for immutability)
    public String getName() {
        return name;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    // Creating modified instances
    public Artist withName(String newName) {
        return new Artist(newName, startYear, endYear);
    }

    public Artist withStartYear(int newStartYear) {
        return new Artist(name, newStartYear, endYear);
    }

    public Artist withEndYear(int newEndYear) {
        return new Artist(name, startYear, newEndYear);
    }

    // Proper equals() implementation
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Artist other = (Artist) obj;

        if (startYear != other.startYear) return false;
        if (endYear != other.endYear) return false;
        return name != null ? name.equals(other.name) : other.name == null;
    }

    // Proper hashCode() implementation
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + startYear;
        result = 31 * result + endYear;
        return result;
    }

    // toString() implementation
    public String toString() {
        return "Artist{" +
               "name='" + name + '\'' +
               ", startYear=" + startYear +
               ", endYear=" + endYear +
               '}';
    }
}

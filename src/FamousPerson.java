import java.time.LocalDate;
import java.time.LocalDateTime;

public class FamousPerson {
    private String name;
    private LocalDate birthday;
    private String knownFor;

    public FamousPerson(String name, LocalDate birthday, String knownFor) {
        this.name = name;
        this.birthday = birthday;
        this.knownFor = knownFor;
    }

    @Override
    public String toString() {
        return "<html>" + name + " was born on this day in year " + birthday.getYear() + "<br>" +
                " and is known for " + knownFor;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getKnownFor() {
        return knownFor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setKnownFor(String knownFor) {
        this.knownFor = knownFor;
    }
}

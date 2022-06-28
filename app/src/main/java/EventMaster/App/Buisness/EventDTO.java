package EventMaster.App.Buisness;

import java.time.LocalDateTime;
import java.util.HashMap;

public class EventDTO {
    private String name;
    private String description;
    private String date;
    private String type;
    private String organizerUsername;
    private long placeId;
    private HashMap<Long,Integer> seatPrices;

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrganizerUsername() {
        return organizerUsername;
    }

    public void setOrganizerUsername(String organizerUsername) {
        this.organizerUsername = organizerUsername;
    }

    public HashMap<Long, Integer> getSeatPrices() {
        return seatPrices;
    }

    public void setSeatPrices(HashMap<Long, Integer> seatPrices) {
        this.seatPrices = seatPrices;
    }

    public EventDTO() {
    }


}

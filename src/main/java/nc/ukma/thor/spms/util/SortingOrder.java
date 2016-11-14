package nc.ukma.thor.spms.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SortingOrder {
	ASC, DESC;
	
    @JsonCreator
    public static SortingOrder forValue(String value) {
        return SortingOrder.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase(); 
    }

}

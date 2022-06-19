
package hirsizlik.mtgacollection.jackson.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "CollationId",
    "SetCode",
    "Count"
})
public class Booster {

    @JsonProperty("CollationId")
    private Integer collationId;
    @JsonProperty("SetCode")
    private String setCode;
    @JsonProperty("Count")
    private Integer count;

    @JsonProperty("CollationId")
    public Integer getCollationId() {
        return collationId;
    }

    @JsonProperty("CollationId")
    public void setCollationId(final Integer collationId) {
        this.collationId = collationId;
    }

    @JsonProperty("SetCode")
    public String getSetCode() {
        return setCode;
    }

    @JsonProperty("SetCode")
    public void setSetCode(final String setCode) {
        this.setCode = setCode;
    }

    @JsonProperty("Count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("Count")
    public void setCount(final Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Booster.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("collationId");
        sb.append('=');
        sb.append(((this.collationId == null)?"<null>":this.collationId));
        sb.append(',');
        sb.append("setCode");
        sb.append('=');
        sb.append(((this.setCode == null)?"<null>":this.setCode));
        sb.append(',');
        sb.append("count");
        sb.append('=');
        sb.append(((this.count == null)?"<null>":this.count));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}

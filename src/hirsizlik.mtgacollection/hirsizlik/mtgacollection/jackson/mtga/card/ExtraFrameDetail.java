
package hirsizlik.mtgacollection.jackson.mtga.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "1",
    "2"
})
public class ExtraFrameDetail {

    @JsonProperty("1")
    private String _1;
    @JsonProperty("2")
    private String _2;

    @JsonProperty("1")
    public String get1() {
        return _1;
    }

    @JsonProperty("1")
    public void set1(String _1) {
        this._1 = _1;
    }

    @JsonProperty("2")
    public String get2() {
        return _2;
    }

    @JsonProperty("2")
    public void set2(String _2) {
        this._2 = _2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ExtraFrameDetail.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("_1");
        sb.append('=');
        sb.append(((this._1 == null)?"<null>":this._1));
        sb.append(',');
        sb.append("_2");
        sb.append('=');
        sb.append(((this._2 == null)?"<null>":this._2));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this._1 == null)? 0 :this._1 .hashCode()));
        result = ((result* 31)+((this._2 == null)? 0 :this._2 .hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ExtraFrameDetail) == false) {
            return false;
        }
        ExtraFrameDetail rhs = ((ExtraFrameDetail) other);
        return (((this._1 == rhs._1)||((this._1 != null)&&this._1 .equals(rhs._1)))&&((this._2 == rhs._2)||((this._2 != null)&&this._2 .equals(rhs._2))));
    }

}

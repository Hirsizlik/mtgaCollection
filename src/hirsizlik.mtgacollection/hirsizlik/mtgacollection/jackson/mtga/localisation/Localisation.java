
package hirsizlik.mtgacollection.jackson.mtga.localisation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "langkey",
    "isoCode",
    "keys"
})
public class Localisation {

    @JsonProperty("langkey")
    private String langkey;
    @JsonProperty("isoCode")
    private String isoCode;
    @JsonProperty("keys")
    private List<Key> keys = null;

    @JsonProperty("langkey")
    public String getLangkey() {
        return langkey;
    }

    @JsonProperty("langkey")
    public void setLangkey(String langkey) {
        this.langkey = langkey;
    }

    @JsonProperty("isoCode")
    public String getIsoCode() {
        return isoCode;
    }

    @JsonProperty("isoCode")
    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    @JsonProperty("keys")
    public List<Key> getKeys() {
        return keys;
    }

    @JsonProperty("keys")
    public void setKeys(List<Key> keys) {
        this.keys = keys;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Localisation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("langkey");
        sb.append('=');
        sb.append(((this.langkey == null)?"<null>":this.langkey));
        sb.append(',');
        sb.append("isoCode");
        sb.append('=');
        sb.append(((this.isoCode == null)?"<null>":this.isoCode));
        sb.append(',');
        sb.append("keys");
        sb.append('=');
        sb.append(((this.keys == null)?"<null>":this.keys));
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
        result = ((result* 31)+((this.langkey == null)? 0 :this.langkey.hashCode()));
        result = ((result* 31)+((this.isoCode == null)? 0 :this.isoCode.hashCode()));
        result = ((result* 31)+((this.keys == null)? 0 :this.keys.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Localisation) == false) {
            return false;
        }
        Localisation rhs = ((Localisation) other);
        return ((((this.langkey == rhs.langkey)||((this.langkey!= null)&&this.langkey.equals(rhs.langkey)))&&((this.isoCode == rhs.isoCode)||((this.isoCode!= null)&&this.isoCode.equals(rhs.isoCode))))&&((this.keys == rhs.keys)||((this.keys!= null)&&this.keys.equals(rhs.keys))));
    }

}

package andella.challenge.com.andellachallenge.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ememobong on 15/03/2017.
 */
public class User {

    @SerializedName("total_count")
    private int totalCount;
    @SerializedName("incomplete_result")
    private boolean incompleteResult;
    @SerializedName("items")
    private List<GitHubUser> userDetails;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<GitHubUser> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<GitHubUser> results) {
        this.userDetails = userDetails;
    }

    public boolean getIncompleteResult() {
        return incompleteResult;
    }

    public void setIncompleteResult(boolean incompleteResult) {
        this.incompleteResult = incompleteResult;
    }

}

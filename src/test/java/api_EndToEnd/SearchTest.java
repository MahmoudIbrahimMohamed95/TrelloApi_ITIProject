package api_EndToEnd;

import apiClients.BoardApi;
import apiClients.OrganizationApi;
import apiClients.SearchApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("Trello")
@Feature("API_Search Management")
@Story("Search on Trello")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class SearchTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("search");
    }
    @Description("Verify creating workspace")
    @Test
    public void verifyCreatingWorkspace(){
        workspaceId=new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("search-spaceName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("search-spaceName"))
                .response.jsonPath().getString("id");
    }
    @Description("Verify creating board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
    public void verifyCreatingBoard(){
        boardId=new BoardApi()
                .createBoard(jsonReader.getJsonData("search-boardName"), workspaceId, jsonReader.getJsonData("search-boardPowerUp"))
                .verifyBoardCreated(jsonReader.getJsonData("search-boardName"), workspaceId)
                .getResponse().jsonPath().getString("id");
    }

    @Description("Verify search on board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard"})
    public void verifySearchOnBoard() {
        new SearchApi()
                .SearchTrello(jsonReader.getJsonData("search-boardName"))
                .verifySearchOnBoard(boardId);
    }
    @Description("Verify search on organization")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifySearchOnBoard"})
    public void verifySearchOnOrganizations() {
        new SearchApi()
                .SearchTrello(jsonReader.getJsonData("search-spaceName"))
                .verifySearchOnOrganization(workspaceId);
    }
    @Description("Delete workspace")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifySearchOnBoard","verifySearchOnOrganizations"})
    public void deleteWorkspace() {
        new OrganizationApi()
                .deleteWorkSpace(workspaceId)
                .verifyWorkSpaceDeleted();
    }
}

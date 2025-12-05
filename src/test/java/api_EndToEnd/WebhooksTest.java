package api_EndToEnd;

import apiClients.BoardApi;
import apiClients.OrganizationApi;
import apiClients.WebhooksApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;


@Epic("Trello")
@Feature("API_WebHook Management")
@Story("Webhooks")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class WebhooksTest {
        private JsonReader jsonReader;
        private String workspaceId;
        private String boardId;
        private String WebhookId;
        @BeforeClass
        protected void setUp() {
            jsonReader = new JsonReader("webhooks");
        }

        @Description("Verify creating workspace")
        @Test
        public void verifyCreatingWorkspace(){
            workspaceId=new OrganizationApi()
                    .createWorkSpace(jsonReader.getJsonData("webhooks-spaceName"))
                    .verifyWorkSpaceCreated(jsonReader.getJsonData("webhooks-spaceName"))
                    .response.jsonPath().getString("id");
        }
        @Description("Verify creating board")
        @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
        public void verifyCreatingBoard(){
            boardId=new BoardApi()
                    .createBoard(jsonReader.getJsonData("webhooks-boardname"), workspaceId, jsonReader.getJsonData("webhooks-boardPowerUP"))
                    .verifyBoardCreated(jsonReader.getJsonData("webhooks-boardname"), workspaceId)
                    .getResponse().jsonPath().getString("id");
        }
    @Description("Verify creating webhook")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard"})
    public void verifyCreatingWebhook(){
          WebhookId=new WebhooksApi()
                    .createWebhook(jsonReader.getJsonData("webhooks-callbackURL"), boardId)
                    .verifyWebhookCreated(jsonReader.getJsonData("webhooks-callbackURL"), boardId)
                    .getResponse().jsonPath().getString("id");
        }

    @Description("Verify retriving webhook")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingWebhook"})
    public void verifyRetrivingWebhook(){
        new WebhooksApi()
                .getWebhook(WebhookId)
                .verifyWebhookRetrivedSuccessfully(WebhookId,jsonReader.getJsonData("webhooks-callbackURL"), boardId);
    }
    @Description("Verify updating webhook")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingWebhook","verifyRetrivingWebhook"})
    public void verifyUpdatingWebhook(){
        new WebhooksApi()
                .updateWebhook(WebhookId,workspaceId)
                .verifyWebhookUpdatedSuccessfully(WebhookId, workspaceId);
    }
    @Description("Verify retriving webhook after updating")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingWebhook","verifyRetrivingWebhook","verifyUpdatingWebhook"})
    public void verifyRetrivingAfterUpdatingWebhook(){
        new WebhooksApi()
                .getWebhook(WebhookId)
                .verifyWebhookUpdatedSuccessfully(WebhookId, workspaceId);
    }
    @Description("Verify deleting webhook")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingWebhook","verifyRetrivingWebhook","verifyUpdatingWebhook","verifyRetrivingAfterUpdatingWebhook"})
    public void verifyDeletingWebhook(){
        new WebhooksApi()
                .deleteWebhook(WebhookId)
                .verifyWebhookDeletedSuccessfully();
    }
    @Description("Verify retriving webhook after deleting")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingWebhook","verifyRetrivingWebhook","verifyUpdatingWebhook","verifyRetrivingAfterUpdatingWebhook","verifyDeletingWebhook"})
    public void verifyRetrivingAfterDeletingWebhook(){
        new WebhooksApi()
                .getWebhook(WebhookId)
                .verifyWebhookRetrivingAfterDeleting();
    }
    @Description("Verify deleting workspace")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingWebhook","verifyRetrivingWebhook","verifyUpdatingWebhook","verifyRetrivingAfterUpdatingWebhook","verifyDeletingWebhook","verifyRetrivingAfterDeletingWebhook"})
    public void deleteWorkspace() {
        new OrganizationApi()
                .deleteWorkSpace(workspaceId)
                .verifyWorkSpaceDeleted();
    }

}

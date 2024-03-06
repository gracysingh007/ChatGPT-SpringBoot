package com.example.controller;

//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.client.RestTemplate;

import com.example.dto.ChatGPTRequest;
import com.example.dto.ChatGPTResponse;

@RestController
@RequestMapping("/bot")
public class BotController {

    @Value("${openai.model}")
    private String model;
    @Value( "${openai.api.url}" )
    private String apiURL;
    @Autowired
    private RestTemplate template;

    @SuppressWarnings("null")
    @GetMapping("/chat")
public String chat() {
    String prompt = "Assume the identity of a data modeler who is proficient in modelling data related to cyber security data log.\n" +
            "The log contains detailed information about a security event related to file upload activity on a system, for example data from Google Workspace drive access log.\n\n" +
            "Following is the data model I want you to use to map field to :\n" +
            "Classes, fields and field descriptions are as follows :\n" +
            "ActivityType : It tells the associated path of the activity and also the sub classes where the Identifier_time: particular activity is located.\n" +
            "Identifier : The Identifier class encapsulates a schema with attributes: time, uniqId, appName, custId.\n" +
            "Timestamp : This specifies the time when the event occurs.\n" +
            "Identifier_UniqId : This a unique string that is different for all events\n" +
            "Identifier_AppName : This specifies the google application where the event occurs.\n" +
            "Identifier_CustId : For a single organization the custId is same for all the employees.\n" +
            "User : The User class encapsulates a schema with attributes: type, email, profileId, and authKey.\n" +
            "userType : This specifies the access level of the user which is of two types access, administration\n" +
            "User.Email : This specifies the email address of the user who performed the action.\n" +
            "User.userId: This specifies the users unique google workspace profile Id.\n" +
            "User_AuthKey : This is an authentication key used to verify the user's identity.\n" +
            "domain Domain name where the action occured.\n" +
            "IPAddress: Ip address from which the log event originated.\n" +
            "Event : It is a subclass that defines the event details of a log event\n" +
            "EventType : This specifies the access level of the user which is of two types access, administration\n" +
            "Event Name : This specifies the name of the process that has been captured during the ocuurance of the event. Some example of the same could be download, edit, etc.\n" +
            "All other fields go in a Parameter map inside Event class\n\n" +
            "Map the following log into the data model provided above:\n" +
            "Policy ID: 250019\n" +
            "Date: January 12, 2024, 12:35:32 IST\n" +
            "Proxy: Agent\n" +
            "Username: abhishekg@cyberassure.com\n" +
            "User Groups: 75569 All Users Bitglass Admins CA-Group\n" +
            "Activity: Uploaded Cloudstorage\n" +
            "Action: Denied\n" +
            "Transaction ID: ZaDkuUk4oJ1jnJoBSjeS9gAABHY\n" +
            "SmartEdge Transaction ID: F41F0969-2F69-4993-81D6-6CAA2F22DB78\n" +
            "Method: POST\n" +
            "Sent Bytes: 89.6 KB\n" +
            "Received Bytes: 528.0 B\n" +
            "File Name: Partner SSNs Names and DOB.pdf\n" +
            "File Extension: pdf\n" +
            "Data Patterns: Payment Card Industry - PCI, PII SSN, PCI Credit-Card, Credit Cards - CCN\n" +
            "Gateway IP: 182.69.181.25\n" +
            "Location: New Delhi, India\n" +
            "Device: Windows 10\n" +
            "Hostname: apgulawin0012\n" +
            "Device GUID: 4C4C4544-0053-5A10-8031-CAC04F385332\n" +
            "User Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36\n" +
            "Cloud App: N/A\n" +
            "Request Port: 443\n" +
            "Domain: dlptest.com\n" +
            "URI Path: /wp-admin/admin-ajax.php\n" +
            "URL: dlptest.com/wp-admin/admin-ajax.php\n" +
            "WebRoot Category: Computer and Internet Info\n" +
            "WebRoot Reputation: 92\n" +
            "URL Categories: Information Technology\n" +
            "URL Reputation: 100\n" +
            "Enterprise App Category: Uncategorized\n" +
            "Enterprise App Score: N/A\n" +
            "Custom Category: DLP Test\n\n" +
            "Return the mapping in a tabular format with first column from data model and second column which contains field from the log, third column containing the mapped value. Do not assume or create new field. Within the table list our unmapped columns from data model and unmapped field and values from log below.";
    ChatGPTRequest request = new ChatGPTRequest(model, prompt); // Assuming "davinci" is the model ID
    ChatGPTResponse chatGptResponse = template.postForObject(apiURL, request, ChatGPTResponse.class);
    // Parse and format the response here
    return chatGptResponse.getChoices().get(0).getMessage().getContent();
}

}

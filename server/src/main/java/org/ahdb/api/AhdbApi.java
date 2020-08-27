package org.ahdb.api;

import org.ahdb.model.*;
import org.ahdb.service.*;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.resteasy.spi.HttpRequest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AhdbApi {

    @Inject ReceiveService receiveService;
    @Inject QueryService queryService;
    @Inject AccountService accountService;
    @Inject ItemDescService itemDescService;

    @Context
    HttpRequest httpRequest;

    @POST @Path("/push")
    @Consumes(MediaType.APPLICATION_JSON)
    public String receive(List<ValuableDataByAccount> valuableDataByAccount) {
        Boolean success = receiveService.receive(io.vavr.collection.List.ofAll(valuableDataByAccount));
        if (success) {
            return "OK";
        }
        return "ERR";
    }

    @GET @Path("/account-stats")
    public AccountStatsVO getStats(@QueryParam("account") String accountId) {
        return accountService.getStats(accountId);
    }

    @GET @Path("/find-items")
    public List<ItemDesc> findItems(@QueryParam("item") String item) {
        return itemDescService.findItems(item);
    }

    @GET @Path("/item-stats")
    public ItemStats queryItemStats(@QueryParam("account") String account, @QueryParam("item") String item) {
        return queryService.queryItemStats(account, item, httpRequest.getRemoteAddress());
    }

    @GET @Path("/all-item-stats")
    public String queryAllItemStats(@QueryParam("account") String account) {
        return queryService.queryAllItemStats(account);
    }

    @GET @Path("/shit")
    @Produces(MediaType.TEXT_PLAIN)
    public String testGet() {
        String port = ConfigProvider.getConfig().getValue("quarkus.http.port", String.class);
        System.out.println(port);
        String batch = ConfigProvider.getConfig().getValue("hibernate.jdbc.batch_size", String.class);
        System.out.println(batch);
        return "OK";
    }
}

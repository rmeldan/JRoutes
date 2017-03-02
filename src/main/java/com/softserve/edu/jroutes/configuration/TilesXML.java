package com.softserve.edu.jroutes.configuration;

import org.apache.tiles.*;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.request.Request;

import java.util.*;

/**
 * Created by ROMAN on 23.02.2017.
 */
public class TilesXML implements DefinitionsFactory {
    private static final Map<String, Definition> tilesDefinitions = new HashMap<>();
    private static final Attribute BASE_DEFINITION = new Attribute("/WEB-INF/tiles/layout.jsp");
    private static final Attribute BASE_DEFINITION_ADMIN = new Attribute("/WEB-INF/tiles/layout-admin.jsp");
    private static final Attribute BASE_DEFINITION_ADMIN_HOME = new Attribute("/WEB-INF/tiles/layout-adminHome.jsp");
    private static final Attribute BASE_DEFINITION_ERRORS = new Attribute("/WEB-INF/tiles/layout-errors.jsp");

    @Override
    public Definition getDefinition(String name, Request tilesContext) {
        return tilesDefinitions.get(name);
    }

    /**
     * <code>Add Apache tiles definitions</code>
     */
    public static void addDefinitions() {
        // Pages Front
        addBaseDefinition("base.definition", "", "");
        addBaseDefinition("index", "Welcome JRoutes", "/WEB-INF/pages/index.jsp");
        addBaseDefinition("userRoutes", "Welcome JRoutes", "/WEB-INF/pages/userRoutes.jsp");
        addBaseDefinition("userRoutesMore", "More about Route", "/WEB-INF/pages/userRoutesMore.jsp");
        addBaseDefinition("userRouteSave", "More about Route", "/WEB-INF/pages/userRouteSave.jsp");

        // Admin Authentication
        addBaseDefinitionAdmin("base.definition.admin", "");
        addBaseDefinitionAdmin("admin", "/WEB-INF/pages/admin.jsp");

        // AdminHome
        addBaseDefinitionAdminHome("base.definition.adminHome", "", "", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("adminHome", "AdminHome", "/WEB-INF/pages/adminHome.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("transport", "Transport", "/WEB-INF/pages/transport.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("editTransport", "Transport", "/WEB-INF/pages/editTransport.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("countries", "Countries", "/WEB-INF/pages/countries.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("cities", "Cities", "/WEB-INF/pages/cities.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("routeconnection", "Routeconnection", "/WEB-INF/pages/routeconnection.jsp", "/WEB-INF/tiles/footer-routeConnection.jsp");
        addBaseDefinitionAdminHome("savedRoutesAdmin", "SavedRoutes", "/WEB-INF/pages/savedRoutesAdmin.jsp", "/WEB-INF/tiles/footer-route.jsp");
        addBaseDefinitionAdminHome("users", "Users", "/WEB-INF/pages/users.jsp", "/WEB-INF/tiles/footer-user.jsp");
        addBaseDefinitionAdminHome("roles", "Roles", "/WEB-INF/pages/roles.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("profile", "Profile", "/WEB-INF/pages/profile.jsp", "/WEB-INF/tiles/footer-admin.jsp");

        // Edit & delete security role pages (forms)
        addBaseDefinitionAdminHome("editSecurityRole", "Profile", "/WEB-INF/pages/editSecurityRole.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("deleteSecurityRole", "Profile", "/WEB-INF/pages/deleteSecurityRole.jsp", "/WEB-INF/tiles/footer-admin.jsp");

        // form to add/remove roles from user
        addBaseDefinitionAdminHome("manageUserSecurityRole", "Profile", "/WEB-INF/pages/userRolesManagment.jsp", "/WEB-INF/tiles/footer-user.jsp");

        // Edit & delete Country pages (forms)
        addBaseDefinitionAdminHome("editCountry", "Profile", "/WEB-INF/pages/editCountry.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("deleteCountry", "Profile", "/WEB-INF/pages/deleteCountry.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("editCity", "Cities", "/WEB-INF/pages/editCity.jsp", "/WEB-INF/tiles/footer-admin.jsp");
        addBaseDefinitionAdminHome("editRouteConn", "Profile", "/WEB-INF/pages/editRouteConn.jsp", "/WEB-INF/tiles/footer-admin.jsp");

        // UserHome
        addBaseDefinitionAdminHome("savedRoutesUser", "SavedRoutes", "/WEB-INF/pages/savedRoutesUser.jsp", "/WEB-INF/tiles/footer-route.jsp");

        // Errors
        addBaseDefinitionErrors("base.definition.errors", "", "");
        addBaseDefinitionErrors("editRouteConnection", "Profile", "/WEB-INF/pages/error/exception_error.jsp");
        addBaseDefinitionErrors("editRouteConnection", "Profile", "/WEB-INF/pages/error/generic_error.jsp");
    }

    /**
     * @param name  <code>Name of the view</code>
     * @param title <code>Page title</code>
     * @param body  <code>Body JSP file path</code>
     *              <p>
     *              <code>Adds default layout definitions</code>
     */
    private static void addBaseDefinition(String name, String title, String body) {
        Map<String, Attribute> attributes = new HashMap<>();
        attributes.put("title", new Attribute(title));
        attributes.put("header", new Attribute("/WEB-INF/tiles/header.jsp"));
        attributes.put("menu", new Attribute("/WEB-INF/tiles/menu.jsp"));
        attributes.put("body", new Attribute(body));
        attributes.put("footer", new Attribute("/WEB-INF/tiles/footer.jsp"));
        tilesDefinitions.put(name, new Definition(name, BASE_DEFINITION, attributes));
    }

    private static void addBaseDefinitionAdmin(String name, String body) {
        Map<String, Attribute> attributes = new HashMap<>();
        attributes.put("title", new Attribute(""));
        attributes.put("header-admin", new Attribute("/WEB-INF/tiles/header-admin.jsp"));
        attributes.put("body", new Attribute(body));
        attributes.put("footer-admin", new Attribute("/WEB-INF/tiles/footer-admin.jsp"));
        tilesDefinitions.put(name, new Definition(name, BASE_DEFINITION_ADMIN, attributes));
    }

    private static void addBaseDefinitionAdminHome(String name, String title, String body, String footerAdmin) {
        Map<String, Attribute> attributes = new HashMap<>();
        attributes.put("title", new Attribute(title));
        attributes.put("header-admin", new Attribute("/WEB-INF/tiles/header-admin.jsp"));
        attributes.put("menu-admin", new Attribute("/WEB-INF/tiles/menu-admin.jsp"));
        attributes.put("body", new Attribute(body));
        attributes.put("footer-admin", new Attribute(footerAdmin));
        tilesDefinitions.put(name, new Definition(name, BASE_DEFINITION_ADMIN_HOME, attributes));
    }

    private static void addBaseDefinitionErrors(String name, String title, String body) {
        Map<String, Attribute> attributes = new HashMap<>();
        attributes.put("title", new Attribute(title));
        attributes.put("header-admin", new Attribute("/WEB-INF/tiles/header-admin.jsp"));
        attributes.put("menu-admin", new Attribute("/WEB-INF/tiles/menu-admin.jsp"));
        attributes.put("body", new Attribute(body));
        attributes.put("footer-admin", new Attribute("/WEB-INF/tiles/footer-admin.jsp"));
        tilesDefinitions.put(name, new Definition(name, BASE_DEFINITION_ERRORS, attributes));
    }
}

package bestbuy;

import com.relevantcodes.extentreports.LogStatus;
import framework.BaseTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class ApiTests extends BaseTest {

    private static final String BASEURL = "http://localhost";
    private static final int PORT = 3030;
    private static final String VERSION = "1.1.0";
    private static final String NOT_FOUND = "NotFound";
    private static final String UPDATED_NAME = "New Name";
    private static final String[] NEW_PRODUCT_FIELDS = new String[]{"id", "name", "type", "upc", "price", "description", "model",
            "createdAt", "updatedAt"};
    private static final Object[] NEW_PRODUCT_VALUES = new Object[]{"New Product", "Hard Good", "12345676", 99.99, "This is a placeholder request for creating a new product.", "NP12345"};
    private static final String[] FIELDS_IN_PRODUCT = new String[]{"id", "name", "type", "price", "upc", "shipping", "description",
            "manufacturer", "model", "url", "image", "createdAt", "updatedAt", "categories"};
    private static final String[] FOUR_FIELDS = new String[]{"id", "name", "createdAt", "updatedAt"};
    private static final String[] FIELDS_IN_CATEGORIES = new String[]{"id", "name", "createdAt", "updatedAt", "subCategories", "categoryPath"};
    private static final String[] FIELDS_IN_STORES = new String[]{"id", "name", "type", "address", "address2", "city",
            "state", "zip", "lat", "lng", "hours", "createdAt", "updatedAt", "services"};
    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(BASEURL)
            .setPort(PORT)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .expectContentType(ContentType.JSON)
            .build();
    private Response responseAll, response;

    @BeforeMethod(alwaysRun = true)
    public void before(Method method) {
        responseSpec.statusCode(200);
    }

    @BeforeClass
    public synchronized void init() {
        RestAssured.responseSpecification = responseSpec;
        RestAssured.requestSpecification = requestSpec;
    }

    // Test check /version GET request and response
    @Test
    public void versionTest() {
        response = given().get(EndPoints.VERSION);
        test.log(LogStatus.INFO, "Checking fields of version response...");
        response.then().body("version", equalTo(VERSION));
    }

    // Test check /healthcheck GET request and response
    @Test
    public void healthCheckTest() {
        response = given().get(EndPoints.HEALTHCECK);
        test.log(LogStatus.INFO, "Checking fields of healthcheck response...");
        response.then().body("documents", Matchers.hasKey("products"), "documents", Matchers.hasKey("stores"), "documents", Matchers.hasKey("categories"));

    }

    // Test check GET all products request and response
    @Test
    public void getAllProductsTest() {
        response = given().get(EndPoints.PRODUCTS, "");
        assertTopFields(response);
        List<Map<String, Object>> products = getData(response);
        for (Map<String, Object> map : products) {
            assertProductsMap(map);
        }
    }

    // Test check GET all categories request and response
    @Test
    public void getAllCategoriesTest() {
        response = given().get(EndPoints.CATEGORIES, "");
        assertTopFields(response);

        List<Map<String, Object>> categories = getData(response);
        for (Map<String, Object> catMap : categories) {
            assertCategoriesMaps(catMap);
        }
    }

    // Test check GET all services request and response
    @Test
    public void getAllServicesTest() {
        response = given().get(EndPoints.SERVICES);
        assertTopFields(response);
        List<Map<String, Object>> services = getData(response);
        for (Map<String, Object> catMap : services) {
            assertFourFields(catMap);
        }
    }

    // Test check GET all services request and response
    @Test
    public void getAllStoresTest() {
        response = given().get(EndPoints.STORES);
        assertTopFields(response);
        List<Map<String, Object>> stores = getData(response);
        for (Map<String, Object> storeMap : stores) {
            assertStoresMaps(storeMap);
        }
    }

    // Test check POST new product request, GET one product request, PATCH product request and DELETE product request
    @Test
    public void addGetUpdateDeleteProductTest() {
        test.log(LogStatus.INFO, "Adding a new product...");
        responseSpec.statusCode(201);
        HashMap newProductMap = getNewProductMap();
        response = given().body(newProductMap).post(EndPoints.PRODUCTS, "");
        Map<String, Object> newProductResponseMap = response.jsonPath().getMap("");
        String newProductID = newProductResponseMap.get(FOUR_FIELDS[0]).toString();
        assertNewProductResponse(newProductResponseMap);
        test.log(LogStatus.INFO, "Getting recently added product...");
        responseSpec.statusCode(200);
        response = given().get(EndPoints.PRODUCTS, newProductID);
        Map<String, Object> oneProductResponseMap = response.jsonPath().getMap("");
        assertNewProductResponse(oneProductResponseMap);
        assertProductsMap(oneProductResponseMap);
        test.log(LogStatus.INFO, "Updating recently added product by new name...");
        newProductMap.remove(FOUR_FIELDS[1]);
        newProductMap.put(FOUR_FIELDS[1], UPDATED_NAME);
        response = given().body(newProductMap).patch(EndPoints.PRODUCTS, "");
        response = given().get(EndPoints.PRODUCTS, newProductID);
        oneProductResponseMap = response.jsonPath().getMap("");
        assertProductsMap(oneProductResponseMap);
        test.log(LogStatus.INFO, "Checking that the product name has been successfully updated...");
        Assert.assertEquals(response.jsonPath().get(FOUR_FIELDS[1]), UPDATED_NAME);
        test.log(LogStatus.INFO, "Deleting recently added product...");
        response = given().delete(EndPoints.PRODUCTS, newProductID);
        test.log(LogStatus.INFO, "Checking that recently added product has been successfully deleted...");
        responseSpec.statusCode(404);
        response = given().get(EndPoints.PRODUCTS, newProductID);
        Assert.assertEquals(response.jsonPath().get(FOUR_FIELDS[1]), NOT_FOUND);
    }

    // Test check GET products request limited by 1 result
    @Test
    public void limitProductsTest() {
        response = given().queryParam("$limit", "1").get(EndPoints.PRODUCTS, "");
        assertTopFields(response);
        test.log(LogStatus.INFO, "Checking 'limits' field equals to 1...");
        Assert.assertEquals(response.jsonPath().get("limit").toString(), "1");
        List<Map<String, Object>> products = getData(response);
        for (Map<String, Object> map : products) {
            assertProductsMap(map);
        }
        test.log(LogStatus.INFO, "Checking that only one product returns...");
        Assert.assertEquals(products.size(), 1, "More than one product returned");
    }

    // Test check GET products request where results are sorted by price descending
    @Test
    public void getProductsFromMostExpensiveDescTest() {
        response = given().queryParam("$sort[price]", "-1").get(EndPoints.PRODUCTS, "");
        assertTopFields(response);
        List<Map<String, Object>> products = getData(response);
        assertDescendingSortPrice(products);
    }


    @AfterMethod
    public void tearDown() {
        reports.endTest(test);
        reports.flush();
    }

    private void assertDescendingSortPrice(List<Map<String, Object>> products) {
        float currentPrice, price;
        price = (Float) products.get(0).get(FIELDS_IN_PRODUCT[3]);
        test.log(LogStatus.INFO, "Checking descending order by price from maximal price " + price + "...");
        for (int i = 1; i < products.size(); i++) {
            currentPrice = (Float) products.get(i).get(FIELDS_IN_PRODUCT[3]);
            Assert.assertFalse(currentPrice > price, "Descending order is broken");
        }
    }

    private void assertFourFields(Map map) {
        for (int i = 0; i < FOUR_FIELDS.length; i++) {
            test.log(LogStatus.INFO, "Checking that json of the object " + map.get("name") + " contains the field " + FOUR_FIELDS[i]);
            Assert.assertTrue(map.containsKey(FOUR_FIELDS[i]));
        }
    }

    private void assertNewProductResponse(Map<String, Object> map) {
        for (int i = 0; i < NEW_PRODUCT_FIELDS.length; i++) {
            test.log(LogStatus.INFO, "Checking that json of the new product " + map.get("name") + " contains the field " + NEW_PRODUCT_FIELDS[i]);
            Assert.assertTrue(map.containsKey(NEW_PRODUCT_FIELDS[i]), "map does not contain key " + NEW_PRODUCT_FIELDS[i]);
            if (i < 6) {
                test.log(LogStatus.INFO, "Checking that the field " + NEW_PRODUCT_FIELDS[i + 1] + "of the response  json of the new product post request equals to posted value " +
                        NEW_PRODUCT_VALUES[i]);
                Assert.assertEquals(map.get(NEW_PRODUCT_FIELDS[i + 1]).toString(), NEW_PRODUCT_VALUES[i].toString(), "The new product response field " + map.get(NEW_PRODUCT_FIELDS[i + 1]) +
                        " does not equal to posted value  " + NEW_PRODUCT_VALUES[i]);
            }
        }
    }


    // Verify product json (along with categories jsons)
    private void assertProductsMap(Map<String, Object> map) {
        for (int i = 0; i < FIELDS_IN_PRODUCT.length; i++) {
            test.log(LogStatus.INFO, "Checking that json of product " + map.get("name") + " contains the field " + FIELDS_IN_PRODUCT[i]);
            Assert.assertTrue(map.containsKey(FIELDS_IN_PRODUCT[i]));
        }
        List<Map> categoriesList = (List) map.get("categories");
        for (Map cat : categoriesList) {
            assertFourFields(cat);
        }
    }

    // Verify every Category json (along with subcategories jsons and categoryPath jsons)
    private void assertCategoriesMaps(Map<String, Object> map) {
        for (int i = 0; i < FIELDS_IN_CATEGORIES.length; i++) {
            test.log(LogStatus.INFO, "Checking that json of category " + map.get("name") + " contains the field " + FIELDS_IN_CATEGORIES[i]);
            Assert.assertTrue(map.containsKey(FIELDS_IN_CATEGORIES[i]));
        }
        List<Map> subcategoriesList = (List) map.get("subCategories");
        for (Map subcat : subcategoriesList) {
            assertFourFields(subcat);
        }
        List<Map> categoryPathList = (List) map.get("categoryPath");
        for (Map pathCat : categoryPathList) {
            assertFourFields(pathCat);
        }
    }

    private List<Map<String, Object>> getData(Response response) {
        return response.jsonPath().getList("data");
    }


    private void assertTopFields(Response response) {
        test.log(LogStatus.INFO, "Checking that json contains the fields total, limit and skip...");
        response.then().body("", Matchers.hasKey("total"), "", Matchers.hasKey("limit"), "" +
                "", Matchers.hasKey("skip"));
    }

    // Verify every Category json (along with subcategories jsons and categoryPath jsons)
    private void assertStoresMaps(Map<String, Object> map) {
        for (int i = 0; i < FIELDS_IN_STORES.length; i++) {
            test.log(LogStatus.INFO, "Checking that json of the store " + map.get("name") + " contains the field " + FIELDS_IN_STORES[i]);
            Assert.assertTrue(map.containsKey(FIELDS_IN_STORES[i]), "field " + FIELDS_IN_STORES[i] + " not found for " + map.get("name"));
        }
        List<Map> servicesList = (List) map.get("services");
        for (Map service : servicesList) {
            assertFourFields(service);
        }
    }

    // Creating a map for PUT Product request
    private HashMap<String, Object> getNewProductMap() {
        HashMap<String, Object> jsonAsMap = new HashMap<String, Object>();
        for (int i = 0; i < NEW_PRODUCT_VALUES.length; i++) {
            jsonAsMap.put(NEW_PRODUCT_FIELDS[i + 1], NEW_PRODUCT_VALUES[i]);
        }
        return jsonAsMap;
    }


    private final class EndPoints {
        private static final String PRODUCTS = "/products/{id}";
        private static final String VERSION = "/version";
        private static final String CATEGORIES = "/categories/{id}";
        private static final String HEALTHCECK = "/healthcheck";
        private static final String SERVICES = "/services";
        private static final String STORES = "/stores";

    }
}
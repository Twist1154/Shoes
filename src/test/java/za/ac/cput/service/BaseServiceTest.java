/*
package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.*;
import za.ac.cput.enums.ProductAttributeType;
import za.ac.cput.factory.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootTest
public abstract class BaseServiceTest {

    @Autowired
    protected CartItemService cartItemService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected ProductService productService;
    @Autowired
    protected ProductSkuService productSkuService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected AddressService addressService;
    @Autowired
    protected OrderDetailsService orderDetailsService;
    @Autowired
    protected OrderItemService orderItemService;
    @Autowired
    protected PaymentDetailsService paymentDetailsService;
    @Autowired
    protected WishlistService wishlistService;
    @Autowired
    protected ProductAttributeService productAttributeService;
    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected SubCategoryService subCategoryService;

    protected Cart cart;
    protected Product product;
    protected ProductSkuService productSku;
    protected User user;
    protected Address address;
    protected OrderDetails orderDetails;
    protected OrderItem orderItem;
    protected PaymentDetails paymentDetails;
    protected Wishlist wishlist;
    protected List<WishlistItem> wishListItems;
    protected Category category;
    protected SubCategory subCategory;

    @BeforeEach
    void setUp() {
        // Setup User
        setupUser();

        // Setup Address
        setupAddress();

        // Setup Product, Category, and SubCategory
        setupCategoryAndProduct();

        // Setup Cart and CartItems
        setupCartAndCartItems();

        // Setup PaymentDetails and OrderDetails
        setupPaymentAndOrderDetails();

        // Setup OrderItem
        setupOrderItem();

        // Setup Wishlist and WishlistItems
        setupWishlistAndItems();

        // Setup ProductAttribute and ProductSkuService
        setupProductAttributeAndSku();
    }

    private void setupUser() {
        // Create or fetch an existing user
        user = userService.read(61L); // Adjust if necessary for tests
        if (user == null) {
            user = new User.Builder()
                    .setFirstName("Rethabile")
                    .setLastName("Ntsekhe")
                    .setEmail("rethabile1154@gmail.com")
                    .setPassword("password")
                    .setRole(Set.of("USER","ADMIN"))
                    .setBirthDate(LocalDate.of(1990, 1, 1))
                    .setPhoneNumber("1234567890")
                    .build();
            userService.create(user);
        }
    }

    private void setupAddress() {
        // Create an Address for the user
        address = new Address.Builder()
                .setUser(user)
                .setTitle("Home")
                .setAddressLine1("Apt 101")
                .setAddressLine2("New York")
                .setCountry("South Africa")
                .setCity("Cape Town")
                .setPostalCode("9320")
                .setPhoneNumber("1234567890")
                .setCreatedAt(LocalDateTime.now())
                .build();
        addressService.create(address);
    }

    private void setupCategoryAndProduct() {
        // Create Category and SubCategory
        category = CategoryFactory.createCategory(
                null, "Art", "Category for Art products", LocalDateTime.now(), null);
        category = categoryService.create(category);

        subCategory = SubCategoryFactory.createSubCategory(
                null, category, "High Tops", "High Top Sneakers", LocalDateTime.now(), null);
        subCategory = subCategoryService.create(subCategory);

        // Create Product
        product = ProductFactory.createProduct(
                null,
                "AirForce 1",
                "All White AirForce 1",
                "Nike AirForce 1",
                "cover img url",
                new ImageUrls.Builder()
                        .setImageUrl1 ("img1.jpg")
                        .setImageUrl2 ("img2.jpg")
                        .setImageUrl3 ("img3.jpg")
                        .setImageUrl4 ("img4.jpg")
                        .build(),
                List.of(subCategory),
                LocalDateTime.now(),
                null
        );
        product = productService.create(product);
    }

    private void setupCartAndCartItems() {
        // Create a Cart for the user
        cart = CartFactory.createCart(
                null,
                user,
                1000.0,
                LocalDateTime.now(),
                null
        );
        cart = cartService.create(cart);

        // Create CartItem linked to Cart and Product
        CartItem cartItem = CartItemFactory.createCartItem(
                null,
                cart,
                product,
                productSkuService.read(1L),
                2
        );
        cartItemService.create(cartItem);
    }

    private void setupPaymentAndOrderDetails() {
        // Create PaymentDetails
        paymentDetails = PaymentDetailsFactory.createPaymentDetails(
                null,
                null,
                100.0,
                "Visa",
                "Success",
                LocalDateTime.now(),
                null
        );
        paymentDetails = paymentDetailsService.create(paymentDetails);

        // Create OrderDetails linked to PaymentDetails
        orderDetails = OrderDetailsFactory.createOrderDetails(
                null,
                user,
                paymentDetails,
                100.0,
                LocalDateTime.now(),
                null
        );
        orderDetails = orderDetailsService.create(orderDetails);
    }

    private void setupOrderItem() {
        // Create OrderItem linked to OrderDetails and Product
        orderItem = OrderItemFactory.createOrderItem(
                null,
                orderDetails,
                product,
                productSkuService.read(1L),
                2,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        orderItemService.create(orderItem);
    }

    private void setupWishlistAndItems() {
        // Create Wishlist for User
        wishlist = new Wishlist.Builder()
                .setUser(user)
                .setCreatedAt(LocalDateTime.now())
                .build();
        wishlist = wishlistService.create(wishlist);

        // Create WishlistItems for Wishlist
        WishlistItem item1 = new WishlistItem.Builder()
                .setProductSubCategories(product)
                .setDateAdded(LocalDateTime.now())
                .setWishlist(wishlist)
                .build();

        WishlistItem item2 = new WishlistItem.Builder()
                .setProductSubCategories(product)
                .setDateAdded(LocalDateTime.now())
                .setWishlist(wishlist)
                .build();

        wishListItems = List.of(item1, item2);
    }

    private void setupProductAttributeAndSku() {
        // Create ProductAttribute and ProductSkuService
        ProductAttribute sizeAttribute = ProductAttributeFactory.createProductAttribute(
                null,
                ProductAttributeType.SIZE,
                "10",
                LocalDateTime.now(),
                null
        );
        productAttributeService.create(sizeAttribute);

        productSku = ProductSkuFactory.createProductSku(
                null,
                product,
                sizeAttribute,
                productAttributeService.read(11L),
                productAttributeService.read(10L),
                "SKU-" + System.currentTimeMillis(),
                100.0,
                10,
                LocalDateTime.now(),
                null
        );
        productSkuService.create(productSku);
    }
}
*/

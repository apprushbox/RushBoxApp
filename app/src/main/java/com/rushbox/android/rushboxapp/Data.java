package com.rushbox.android.rushboxapp;

import com.rushbox.android.rushboxapp.model.Order;
import com.rushbox.android.rushboxapp.model.Product;
import com.rushbox.android.rushboxapp.model.Provider;
import com.rushbox.android.rushboxapp.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Ronner on 16-12-2015.
 */
public class Data {
    private static Data INSTANCE = null;
    private static ArrayList<Provider> stores;
    private static ArrayList<Product> products;
    private static ArrayList<User> users;
    private static ArrayList<Order> ordersReceived;
    private Order order;
    public int countOrder = 0;
    public int orderReceived = 2;

    private Data() {
        users = new ArrayList<User>();
//        users.add(new User(1, "Jhon Smith", "2398 Harding Av", "+1 407 543 32 11", 0, 0));
//        users.add(new User(2, "Taylor Addams", "9769 Collins Av", "+1 407 322 12 24", 0, 0));
//        users.add(new User(3, "Bob Pharrell", "1265 5th Av", "+1 407 765 32 09", 0, 0));

//        ordersReceived = new ArrayList<>();
//        Order newOrder = new Order();
//        newOrder.setId(1);
//        newOrder.setStoreId(12);
//        newOrder.setUserId(1);
//        ArrayList<Product> productOrders = new ArrayList<>();
//        productOrders.add(new Product(35, "Beer Six Pack Miller", "Description", 12, "", 6.99, false, null));
//        newOrder.setListProducts(productOrders);
//        ordersReceived.add(newOrder);
//
//        newOrder = new Order();
//        newOrder.setId(2);
//        newOrder.setStoreId(12);
//        newOrder.setUserId(2);
//        productOrders = new ArrayList<>();
//        productOrders.add(new Product(36, "Beer Six Pack Miller", "Description", 12, "", 6.99, false, null));
//        newOrder.setListProducts(productOrders);
//        ordersReceived.add(newOrder);
//
//        newOrder = new Order();
//        newOrder.setId(1);
//        newOrder.setStoreId(12);
//        newOrder.setUserId(3);
//        productOrders = new ArrayList<>();
//        productOrders.add(new Product(37, "Beer Six Pack Heineken", "Description", 12, "", 8.99, false, null));
//        productOrders.add(new Product(38, "Soda", "Description", 12, "", 0.99, false, null));
//        newOrder.setListProducts(productOrders);
//        ordersReceived.add(newOrder);

//        stores = new ArrayList<Store>();
//        stores.add(new Store(1, "Giovanni Store", "", "+1 407 876 54 32", 3.2, 0, 0, 5, 2, 3, 7, 5, 6, true, "Av, St. North West"));
//        stores.add(new Store(2, "Corner Store", "", "+1 407 830 32 11", 2.4, 0, 0, 1, 7, 3, 7, 5, 6, true, "Av, St. North West"));
//        stores.add(new Store(3, "Cozine Plumbing, Inc", "", "+1 407 876 54 32", 1.3, 0, 0, 15, 2, 3, 4, 5, 6, true, "3251 Hollywood Blv"));
//        stores.add(new Store(4, "Battery Int'L", "", "+1 407 830 32 11", 1.8, 0, 0, 6.50, 2, 3, 20, 5, 6, true, "4521 Grand St."));
//        stores.add(new Store(5, "Flowers Love", "", "+1 407 876 54 32", 1.2, 0, 0, 5, 2, 3, 12, 5, 6, true, "325 Forest Lane St."));
//        stores.add(new Store(6, "My Taylor, Inc", "", "+1 407 830 32 11", 2.4, 0, 0, 0, 2, 3, 20, 5, 6, false, "1123 Int. Drive"));
//        stores.add(new Store(7, "Babysit Annie", "", "+1 407 876 54 32", 1.2, 0, 0, 0, 2, 3, 10, 5, 6, true, "Av, St. North West"));
//        stores.add(new Store(8, "Babysit Jay", "", "+1 407 830 32 11", 2.4, 0, 0, 0, 2, 3, 10, 5, 6, true, "Av, St. North West"));
//        stores.add(new Store(9, "China Town Restaurant", "", "+1 407 876 54 32", 1.0, 0, 0, 10, 2, 3, 20, 5, 6, true, "Av, St. North West"));
//        stores.add(new Store(10, "Italian Food Online", "", "+1 407 830 32 11", 0.4, 0, 0, 15, 2, 3, 40, 5, 6, false, "Av, St. North West"));
//        stores.add(new Store(11, "Super MealMarket", "", "+1 407 830 32 11", 2.0, 0, 0, 1, 3, 3, 5, 5, 6, true, "Av, St. North West"));
//        stores.add(new Store(12, "Beer Corporation", "", "+1 407 830 32 11", 2.0, 0, 0, 6, 3, 3, 5, 5, 6, true, "Av, St. North West"));
//        stores.add(new Store(13, "Shell Gas Station", "", "+1 407 830 32 11", 0.2, 0, 0, 5, 3, 3, 5, 5, 6, true, "1330 Main St."));
//        stores.add(new Store(14, "Exxon Gas", "", "+1 407 830 32 11", 0.2, 0, 0, 6, 3, 3, 5, 5, 6, true, "2555 Howell Ave."));
//        stores.add(new Store(15, "Dental Now Inc", "", "+1 407 830 32 11", 0.2, 0, 0, 0, 3, 3, 5, 5, 6, true, "155 Houghes Ave."));
//        stores.add(new Store(16, "Dental Assoc", "", "+1 407 830 32 11", 0.2, 0, 0, 3, 0, 3, 5, 5, 6, false, "2115 Grand St."));
//        stores.add(new Store(17, "Pharmacy Now Inc", "", "+1 407 830 32 11", 0.2, 0, 0, 3, 3, 3, 5, 5, 6, true, "1550 Canal St."));
//        stores.add(new Store(18, "Tires Unlimited", "", "+1 407 830 32 11", 0.2, 0, 0, 3, 3, 3, 5, 5, 6, true, "451 Grand Station"));
//        stores.add(new Store(19, "Tow Land Inc", "", "+1 407 830 32 11", 0.2, 0, 0, 3, 3, 3, 5, 5, 6, true, "530 O Higgins Ave."));
//        stores.add(new Store(20, "LOCKSMITH CHARLIE LTD", "", "+1 407 830 32 11", 0.2, 0, 0, 3, 3, 3, 5, 5, 6, true, "3445  Collins Av # 1010"));
//        stores.add(new Store(21, "KEYS UNLIMITED", "", "+1 407 830 32 11", 0.2, 0, 0, 3, 3, 3, 5, 5, 6, true, "3445  Collins Av # 1010"));
//        stores.add(new Store(22, "EMERGENCY LOCKSMITH", "", "+1 407 830 32 11", 0.2, 0, 0, 3, 3, 3, 5, 5, 6, true, "3445  Collins Av # 1010"));
//        stores.add(new Store(23, "Orange Store", "", "+1 407 876 54 32", 1.2, 0, 0, 5, 2, 3, 7, 5, 6, false, "Av, St. North West"));
//        stores.add(new Store(24, "Fast Shop", "", "+1 407 830 32 11", 2.4, 0, 0, 1, 7, 3, 7, 5, 6, false, "Av, St. North West"));
//        stores.add(new Store(25, "Splash Plumbing, Corp", "", "+1 407 876 54 32", 1.3, 0, 0, 15, 2, 3, 4, 5, 6, true, "3251 Angel Blv"));
//        stores.add(new Store(26, "Flowers Citymar", "", "+1 407 876 54 32", 1.2, 0, 0, 5, 2, 3, 12, 5, 6, true, "3212 Peace Av."));
//        stores.add(new Store(27, "Best Taylor, Inc", "", "+1 407 830 32 11", 2.4, 0, 0, 0, 2, 3, 15, 5, 6, true, "1123 Jhonnson Blv. "));
//
//        products = new ArrayList<Product>();
//
//        products.add(new Product(1, "Almond Milk ", "Description", 1, "", 1.40, false, null));
//        products.add(new Product(2, "Mayo", "Description", 1, "", 6.40, false, null));
//        products.add(new Product(3, "Ketchup", "Description", 1, "", 1.70, false, null));
//        products.add(new Product(4, "Milk Instant Dry", "Description", 1, "", 3.80, false, null));
//        products.add(new Product(5, "Orange Juice", "Description", 1, "", 3.60, false, null));
//        products.add(new Product(6, "Beer", "Description", 1, "", 12.90, false, null));
//        products.add(new Product(7, "Whisky", "Description", 1, "", 19.40, false, null));
//        products.add(new Product(8, "Soda", "Description", 1, "", 3.40, false, null));
//        products.add(new Product(9, "Eggs", "Description", 1, "", 3.40, false, null));
//        products.add(new Product(10, "Peanut Butter", "Description", 1, "", 3.40, false, null));
//        products.add(new Product(11, "Milk", "Description", 1, "", 3.40, false, null));
//        products.add(new Product(12, "Bread", "Description", 1, "", 3.40, false, null));
//        products.add(new Product(13, "Water", "Description", 1, "", 2.40, false, null));
//        products.add(new Product(14, "Aspirin", "Description", 1, "", 1.00, false, null));
//        products.add(new Product(15, "Teflon", "Description", 1, "", 0.99, false, null));
//        products.add(new Product(16, "Cake", "Description", 1, "", 2.20, false, null));
//        products.add(new Product(17, "Cookies", "Description", 1, "", 1.99, false, null));
//        products.add(new Product(18, "Milk Fat Reduced 2%", "Description", 1, "", 13.40, false, null));
//        products.add(new Product(19, "Soda", "Description", 1, "", 1.00, false, null));
//
//        products.add(new Product(20, "Almond Milk ", "Description", 2, "", 1.40, false, null));
//        products.add(new Product(21, "Mayo", "Description", 2, "", 6.40, false, null));
//        products.add(new Product(22, "Ketchup", "Description", 2, "", 1.70, false, null));
//        products.add(new Product(23, "Milk Instant Dry", "Description", 2, "", 3.80, false, null));
//        products.add(new Product(24, "Orange Juice", "Description", 2, "", 3.60, false, null));
//        products.add(new Product(25, "Beer", "Description", 2, "", 12.90, false, null));
//        products.add(new Product(26, "Whisky", "Description", 2, "", 19.40, false, null));
//        products.add(new Product(27, "Soda", "Description", 2, "", 3.40, false, null));
//        products.add(new Product(28, "Eggs", "Description", 2, "", 3.40, false, null));
//        products.add(new Product(29, "Peanut Butter", "Description", 2, "", 3.40, false, null));
//        products.add(new Product(30, "Milk", "Description", 2, "", 3.40, false, null));
//        products.add(new Product(31, "Bread", "Description", 2, "", 3.40, false, null));
//        products.add(new Product(32, "Water", "Description", 2, "", 2.40, false, null));
//        products.add(new Product(33, "Aspirine", "Description", 2, "", 1.00, false, null));
//        products.add(new Product(34, "Teflon", "Description", 2, "", 0.99, false, null));
//        products.add(new Product(35, "Cake", "Description", 2, "", 2.20, false, null));
//        products.add(new Product(36, "Cookies", "Description", 2, "", 1.99, false, null));
//        products.add(new Product(37, "Milk Fat Reduced 2%", "Description", 2, "", 13.40, false, null));
//        products.add(new Product(38, "Soda", "Description", 2, "", 1.00, false, null));
//
//        products.add(new Product(39, "Vodka Tanqueray 750cc", "Description", 2, "", 12.00, false, null));
//        products.add(new Product(40, "Vodka Stousnaya 750cc", "Description", 2, "", 16.00, false, null));
//        products.add(new Product(41, "Vodka Park 750cc", "Description", 2, "", 16.00, false, null));
//        products.add(new Product(42, "Whisky Black Label 750cc", "Description", 2, "", 22.00, false, null));
//        products.add(new Product(43, "Whisky Red Label 750cc", "Description", 2, "", 18.00, false, null));
//        products.add(new Product(44, "Whisky 100 Pipers 750cc", "Description", 2, "", 15.00, false, null));
//        products.add(new Product(45, "Whisky Blue Label 750cc", "Description", 2, "", 65.00, false, null));
//        products.add(new Product(46, "Vodka Polish 750cc", "Description", 2, "", 15.00, false, null));
//        products.add(new Product(47, "Vodka Kussian 1500cc", "Description", 2, "", 42.00, false, null));
//        products.add(new Product(48, "Vodka Moon 750cc", "Description", 2, "", 12.00, false, null));
//        products.add(new Product(49, "Whisky Buchannan 750cc", "Description", 2, "", 22.00, false, null));
//        products.add(new Product(50, "Whisky Ballantine 750cc", "Description", 2, "", 26.00, false, null));
//        products.add(new Product(51, "Whisky Drunkar", "Description", 2, "", 36.00, false, null));
//        products.add(new Product(52, "Gin Tanqueray 750cc", "Description", 2, "", 18.00, false, null));
//        products.add(new Product(53, "Gin Powel", "Description", 2, "", 22.00, false, null));
//        products.add(new Product(54, "Cake", "Description", 2, "", 2.20, false, null));
//        products.add(new Product(55, "Cookies", "Description", 2, "", 1.99, false, null));
//        products.add(new Product(56, "Milk Fat Reduced 2%", "Description", 2, "", 13.40, false, null));
//        products.add(new Product(57, "Soda", "Description", 2, "", 1.00, false, null));
//
//        products.add(new Product(58, "Almond Milk ", "Description", 23, "", 1.40, false, null));
//        products.add(new Product(59, "Mayo", "Description", 23, "", 6.40, false, null));
//        products.add(new Product(60, "Ketchup", "Description", 23, "", 1.70, false, null));
//        products.add(new Product(61, "Milk Instant Dry", "Description", 23, "", 3.80, false, null));
//        products.add(new Product(62, "Orange Juice", "Description", 23, "", 3.60, false, null));
//        products.add(new Product(63, "Beer", "Description", 23, "", 12.90, false, null));
//        products.add(new Product(64, "Whisky", "Description", 23, "", 19.40, false, null));
//        products.add(new Product(65, "Soda", "Description", 23, "", 3.40, false, null));
//        products.add(new Product(66, "Eggs", "Description", 23, "", 3.40, false, null));
//        products.add(new Product(67, "Peanut Butter", "Description", 23, "", 3.40, false, null));
//        products.add(new Product(68, "Milk", "Description", 23, "", 3.40, false, null));
//        products.add(new Product(69, "Bread", "Description", 23, "", 3.40, false, null));
//        products.add(new Product(70, "Water", "Description", 23, "", 2.40, false, null));
//        products.add(new Product(71, "Aspirine", "Description", 23, "", 1.00, false, null));
//        products.add(new Product(72, "Teflon", "Description", 23, "", 0.99, false, null));
//        products.add(new Product(73, "Cake", "Description", 23, "", 2.20, false, null));
//        products.add(new Product(74, "Cookies", "Description", 23, "", 1.99, false, null));
//        products.add(new Product(75, "Milk Fat Reduced 2%", "Description", 23, "", 13.40, false, null));
//        products.add(new Product(76, "Soda", "Description", 23, "", 1.00, false, null));
//
//
//        products.add(new Product(77, "Battery 12V 700 Amps", "Description", 4, "", 24.99, false, null));
//        products.add(new Product(78, "Battery Installation", "Help in 20 Minutes! $35.00 Flat + Materials", 4, "", 35.00, true, null));
//        products.add(new Product(79, "Flat Tire Repair", "Help in 30 Minutes! $55.00 Flat Tire Repair + Materials", 4, "", 55.00, true, null));
//        products.add(new Product(80, "Tire", "Description", 4, "", 33.29, false, null));
//
//        products.add(new Product(81, "Plumber", "Help in 15 Minutes! $35.00 Housecall Plus Materials", 3, "", 35.00, true, null));
//        products.add(new Product(82, "Plumber", "Help in 10 Minutes! $38.99 Housecall Plus Materials", 25, "", 38.99, true, null));
//
//        try {
//            products.add(new Product(83, "Babysit", "Monday December 28th From 7:00 p.m. $8/hr", 7, "", 8, true, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-12-28 19:00:00")));
//            products.add(new Product(84, "Babysit", "Wednesday December 30th From 8:00 p.m. $8/hr", 7, "", 8, true, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-12-30 20:00:00")));
//            products.add(new Product(85, "Babysit", "Friday January 1st From 5:00 p.m. $9/hr", 7, "", 9, true, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2016-01-01 17:00:00")));
//            products.add(new Product(86, "Babysit", "Tuesday December 29th From 9:00 p.m. $8/hr", 8, "", 8, true, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-12-29 21:00:00")));
//            products.add(new Product(87, "Babysit", "Friday January 1st From 8:00 p.m. $7/hr", 8, "", 7, true, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2016-01-01 20:00:00")));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        products.add(new Product(88, "Red Roses 12 Red Roses", "", 5, "", 24.00, false, null));
//        products.add(new Product(89, "White Roses 6 White Roses", "", 5, "", 12.00, false, null));
//        products.add(new Product(90, "Caledonians 10 Caledonians", "", 5, "", 14.00, false, null));
//
//        products.add(new Product(91, "Red Roses 12 Red Roses", "", 26, "", 24.00, false, null));
//        products.add(new Product(92, "White 10 White Roses", "", 26, "", 11.00, false, null));
//        products.add(new Product(93, "Daisies 12 Daisies", "", 26, "", 15.00, false, null));
//
//        products.add(new Product(94, "Tayloring", "OFFER! 10 SHIRTS FOR $10.00. Free Pick up", 27, "", 10.00, true, null));
//        products.add(new Product(95, "Tayloring", "All tayloring in premises. Rush. Pick up $25.00 + Alterations. 15 Minutes", 27, "", 25.00, true, null));
//
//        //products.add(new Product(96, "Soda", "Description", 11, "", 0.99, false, null));
//        products.add(new Product(97, "Beer Six Pack Miller", "Description", 12, "", 6.99, false, null));
//        products.add(new Product(98, "Beer Six Pack Budwizer", "Description", 12, "", 7.99, false, null));
//        products.add(new Product(99, "Beer Six Pack Heineken", "Description", 12, "", 8.99, false, null));
//        products.add(new Product(100, "Soda", "Description", 12, "", 0.99, false, null));
//
//        products.add(new Product(101, "Propane 5 Gal", "Description", 13, "", 39.99, false, null));
//        products.add(new Product(102, "Air Filter", "Description", 13, "", 5.99, false, null));
//        products.add(new Product(103, "Steering Fluid", "Description", 13, "", 6.99, false, null));
//
//        products.add(new Product(103, "Car Wash", "Description", 14, "", 15, false, null));
//        products.add(new Product(104, "Propane 5 Gal", "Description", 14, "", 38.99, false, null));
//        products.add(new Product(105, "Battery 12 V", "Description", 14, "", 49.99, false, null));
//
//        products.add(new Product(106, "Emergency", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Now Open", 15, "", 0.00, true, null));
//        products.add(new Product(107, "Tooth", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Now Open", 15, "", 0.00, true, null));
//        products.add(new Product(108, "Ache", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Now Open", 15, "", 0.00, true, null));
//        products.add(new Product(109, "Dental", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Now Open", 15, "", 0.00, true, null));
//        products.add(new Product(110, "Teeth", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Now Open", 15, "", 0.00, true, null));
//        products.add(new Product(111, "Pain", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Now Open", 15, "", 0.00, true, null));
//
//        products.add(new Product(112, "Emergency", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Closed Now", 16, "", 0.00, true, null));
//        products.add(new Product(113, "Root", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Closed Now", 16, "", 0.00, true, null));
//        products.add(new Product(114, "Canal", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Closed Now", 16, "", 0.00, true, null));
//        products.add(new Product(115, "Dental", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Closed Now", 16, "", 0.00, true, null));
//        products.add(new Product(116, "Teeth", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Closed Now", 16, "", 0.00, true, null));
//        products.add(new Product(117, "Pain", "Emergency, Teeth, Pan, Root, Canal, Dental. Emergency Closed Now", 16, "", 0.00, true, null));
//
//        products.add(new Product(118, "Alcohol", "", 17, "", 1.00, false, null));
//        products.add(new Product(119, "Gauze", "", 17, "", 1.00, false, null));
//        products.add(new Product(120, "Aspirin", "", 17, "", 1.00, false, null));
//        products.add(new Product(121, "Cough Syrup", "", 17, "", 3.00, false, null));
//        products.add(new Product(122, "Cortizone", "", 17, "", 1.00, false, null));
//        products.add(new Product(123, "Headache Pills", "", 17, "", 1.45, false, null));
//        products.add(new Product(124, "Sun Screen", "", 17, "", 8.00, false, null));
//
//
//        products.add(new Product(125, "Repair", "On the road repairs $50.00 + Materials", 18, "", 50.00, true, null));
//        products.add(new Product(126, "Flat Tire Repair", "Flat Tire Repair $45.00", 18, "", 45.00, true, null));
//        products.add(new Product(127, "Tow", "Towing 15 miles radius $70.00", 18, "", 70.00, true, null));
//
//        products.add(new Product(128, "Tow", "Towing 10 miles radius $60.00", 19, "", 60.00, true, null));
//
//        products.add(new Product(129, "House call key", "House call key replacement $36.00", 20, "", 36, true, null));
//        products.add(new Product(130, "Extra Key", "", 20, "", 4.00, false, null));
//        products.add(new Product(131, "Car lock", "Car lock opening $25.00", 20, "", 25, true, null));
//
//        products.add(new Product(132, "Housecall opening", "Housecall opening  $45.00", 21, "", 45, true, null));
//        products.add(new Product(133, "Extra Key", "", 21, "", 4, false, null));
//        products.add(new Product(134, "Car lock", "Car lock opening", 21, "", 35, true, null));
    }

    public ArrayList<Order> getOrdersReceived() {
        return ordersReceived;
    }

    public void setOrdersReceived(ArrayList<Order> ordersReceived) {
        Data.ordersReceived = ordersReceived;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public Order getOrderReceivedById(Integer id) {
        for (Order order : ordersReceived) {
            if (order.getId() == id)
                return order;
        }
        return null;
    }

//    public User getUserById(Integer id) {
//        for (User user : users) {
//            if (user.getId() == id)
//                return user;
//        }
//        return null;
//    }

    public void setUsers(ArrayList<User> users) {
        Data.users = users;
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Data();
        }
    }

    public static Data getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

    public Order getOrder() {
        if (order == null) {
            order = new Order();
        }
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    //public ArrayList<Store> getStores() {
        //return stores;
    //}

//    public void setStores(ArrayList<Store> stores) {
//        this.stores = stores;
//    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    //public void setProducts(ArrayList<Product> products) {
    // this.products = products;
    //}

//    public MyObject getProductsBySearch(String textSearched) {
//        ArrayList<Product> productsR = new ArrayList<>();
//        ArrayList<Store> storesR = new ArrayList<>();
//        for (Product product : products) {
//            if (product.getName().toLowerCase().startsWith(textSearched)) {
//                productsR.add(product);
//                if (!storesR.contains(getStoreById(product.getStoreId())))
//                    storesR.add(getStoreById(product.getStoreId()));
//            }
//        }
//        MyObject myObject = new MyObject();
//        myObject.products = productsR;
//        myObject.stores = storesR;
//        return myObject;
//    }
//
//    public MyObject getProductsBySearch(String textSearched, Integer storeId) {
//        ArrayList<Product> productsR = new ArrayList<>();
//        ArrayList<Store> storesR = new ArrayList<>();
//        for (Product product : products) {
//            //if (product.getStoreId() == storeId && !product.isService()) {
//            if (product.getStoreId() == storeId) {
//                if (product.getName().toLowerCase().startsWith(textSearched)) {
//                    productsR.add(product);
//                    if (!storesR.contains(getStoreById(product.getStoreId())))
//                        storesR.add(getStoreById(product.getStoreId()));
//                }
//            }
//        }
//        MyObject myObject = new MyObject();
//        myObject.products = productsR;
//        myObject.stores = storesR;
//        return myObject;
//    }
//
//    public Store getStoreById(Integer storeId) {
//        for (Store store : stores) {
//            if (store.getId() == storeId)
//                return store;
//        }
//        return null;
//    }
//
//    public Product getProductById(int productId) {
//        for (Product product : products) {
//            if (product.getId() == productId) {
//                return product;
//            }
//        }
//        return null;
//    }
//
//    public ArrayList<String> getStringProducts(String textSearched) {
//        ArrayList<String> productsString = new ArrayList<String>();
//        int i = 0;
//        for (Product product : products) {
//            if (product.getName().toLowerCase().contains(textSearched.toLowerCase())) {
//                if (!productsString.contains(product.getName().toLowerCase()))
//                    productsString.add(product.getName().toLowerCase());
//                i++;
//            }
//            if (i >= 5)
//                break;
//        }
//        return productsString;
//    }
//
//    public ArrayList<String> getStringProducts() {
//        ArrayList<String> productsString = new ArrayList<String>();
//        for (Product product : products) {
//            if (!productsString.contains(product.getName().toUpperCase()))
//                productsString.add(product.getName().toUpperCase());
//        }
//        return productsString;
//    }

}

package com.example.project_sem_4.seed;
import com.example.project_sem_4.database.dto.ServiceDTO;
import com.example.project_sem_4.database.entities.*;
import com.example.project_sem_4.database.repository.*;
import com.example.project_sem_4.enum_project.GenderEnum;
import com.example.project_sem_4.enum_project.RoleEnum;
import com.example.project_sem_4.enum_project.StatusEnum;
import com.example.project_sem_4.enum_project.TimeBookingEnum;
import com.example.project_sem_4.service.authen.AuthenticationService;
import com.example.project_sem_4.service.authen.RoleService;
import com.example.project_sem_4.service.service.ServiceHair;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SeedTest implements CommandLineRunner {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    MembershipClassRepository membershipClassRepository;

    @Autowired
    TypeServiceRepository typeServiceRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    ServiceHair serviceHair;

    private boolean createSeed = true;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ServiceRepository serviceModelRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private OrderDetailRepository orderDetailRepository;




    @Override
    public void run(String... args) throws Exception {
        Random rand = new Random();
        Calendar cal = Calendar.getInstance();
        String sql;
        if (createSeed) {
            List<Branch> branches = branchRepository.findAll();
            if(branches.size() == 0){
                Integer[] statuss = {0, StatusEnum.ACTIVE.status};

                String hotLine = "0773776942";

                    String thumbnail1 = "https://lawkey.vn/wp-content/uploads/2016/10/72358PICV9G.jpg";
                    String address1 = "H?? N???i";
                    String name1 = "C?? S??? T??n ?????c Th???ng";


                String thumbnail2 = "https://2doctor.org/wp-content/uploads/2021/08/dia-chi-cat-toc-nam-dep-o-ha-noi.jpg";
                String address2 = "H??? Ch?? Minh";
                String name2 = "C?? S??? Ho??ng Qu???c Vi???t";

                    Date dateBranch = getRamdomDate(2020, 2020, "yyyy-MM-dd");
                    Integer status = statuss[rand.nextInt(statuss.length)];
                    String bookingDateBranch = String.valueOf(dateBranch.getDate());
                    if (dateBranch.getDate() < 10) {
                        bookingDateBranch = 0 + String.valueOf(dateBranch.getDate());
                    }

                    int validateMonthBranch = dateBranch.getMonth() + 1;
                    String bookingMonthBranch = String.valueOf(validateMonthBranch);
                    if (validateMonthBranch < 10) {
                        bookingMonthBranch = 0 + String.valueOf(validateMonthBranch);
                    }

                    cal.setTime(dateBranch);
                    String bookingYearBranch = String.valueOf(cal.get(Calendar.YEAR));

                   String sql1 = "INSERT INTO branchs VALUES (" +
                       1 + ","+
                           '"' + bookingYearBranch + "-" + bookingMonthBranch + "-" + bookingDateBranch + '"' + ","+
                           status + ","+
                           '"' + bookingYearBranch + "-" + bookingMonthBranch + "-" + bookingDateBranch + '"' + ","+
                            '"' + address1+ '"' + ","+
                            '"' + hotLine+ '"' + ","+
                            '"' + name1+ '"' + ","+
                            '"' + thumbnail1+ '"' + ")"
                   ;
                   jdbcTemplate.update(
                           sql1);
                String sql2 = "INSERT INTO branchs VALUES (" +
                        2 + ","+
                        '"' + bookingYearBranch + "-" + bookingMonthBranch + "-" + bookingDateBranch + '"' + ","+
                        status + ","+
                        '"' + bookingYearBranch + "-" + bookingMonthBranch + "-" + bookingDateBranch + '"' + ","+
                        '"' + address2+ '"' + ","+
                        '"' + hotLine+ '"' + ","+
                        '"' + name2+ '"' + ","+
                        '"' + thumbnail2+ '"' + ")"
                        ;
                jdbcTemplate.update(
                        sql2);

            }

            MembershipClass membershipClass = membershipClassRepository.findById(1).orElse(null);
            if (membershipClass == null) {
                membershipClassRepository.save(MembershipClass.builder().name("H???ng S").build());
                membershipClassRepository.save(MembershipClass.builder().name("H???ng A").build());
                membershipClassRepository.save(MembershipClass.builder().name("H???ng B").build());
                membershipClassRepository.save(MembershipClass.builder().name("H???ng C").build());
                membershipClassRepository.save(MembershipClass.builder().name("H???ng D").build());

                roleRepository.save(Role.builder().name(RoleEnum.ADMIN.role).build());
                roleRepository.save(Role.builder().name(RoleEnum.RECEPTIONISTS.role).build());
                roleRepository.save(Role.builder().name(RoleEnum.STAFF.role).build());
                roleRepository.save(Role.builder().name(RoleEnum.CUSTOMER_CARE.role).build());
                roleRepository.save(Role.builder().name(RoleEnum.CUSTOMER.role).build());
            }

            Account Walk_In_Guest = accountRepository.findById(1).orElse(null);
            if (Walk_In_Guest == null) {
                authenticationService.saveWalk_In_Guest();
                createAccount("ADMIN");
                createAccount("RECEPTIONISTS");
                createAccount("STAFF1");
                createAccount("CUSTOMER_CARE");
                createAccount("CUSTOMER");
                createAccount("STAFF2");
                createAccount("STAFF3");
                createAccount("STAFF4");
            }


            List<TypeService> typeServices = typeServiceRepository.findAll();
            if (typeServices.size() == 0) {
                typeServiceRepository.save(new TypeService(1,"C???t T??c", 1));
                typeServiceRepository.save(new TypeService(2,"G???i ?????u", 1));
                typeServiceRepository.save(new TypeService(3,"Massage", 1));
                typeServiceRepository.save(new TypeService(4,"Nhu???m t??c", 1));
                typeServiceRepository.save(new TypeService(5,"Ch??m s??c da", 1));

                serviceHair.createService(ServiceDTO.builder().name("U???n H??n Qu???c 8 c???p ?????")
                        .description("Thu???c u???n ?????u ti??n tr??n th??? gi???i lo???i b??? th??nh ph???n Hydrochloride kh???i Cysteamine kh??ng g??y h???i da ?????u, cam k???t kh??ng g??y r???ng t??c.")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/Service/uon-han-quoc/ep-side.jpg")
                        .typeServiceId(1).price(300000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("Nhu???m m??u th???i trang")
                        .description("Nhu???m m??u th???i trang\n" +
                                "B???ng m??u m???i chia l??m 4 g??i nhu???m theo tone m??u kh??c nhau ph?? h???p v???i t???ng ?????i t?????ng ?????c bi???t: Elegant Black, Modern Man, Lady Killah v?? Fashionisto\n" +
                                "V???i g??i m??u Modern Man n??y, 30Shine mu???n h?????ng t???i m???t m??u ??en classic, ??em ?????n s??? thanh l???ch, t??t l???i phong ????? cho ng?????i ????n ??ng Vi???t Nam")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/landingpage/nhuom/nau/30shine-Shinecolor-nhuom-nau-cao-cap-dinh-hinh-nguoi-dan-ong-hien-dai-2.jpg")
                        .typeServiceId(4).price(350000.0)
                        .build());

                serviceHair.createService(ServiceDTO.builder().name("Massage c??? vai g??y")
                        .description("Massage c??? vai g??y cam ng???t, ch??n t???ng m??y")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/Service/tat-ca-dich-vu/20220105-massage-co-vai-gay.jpg")
                        .typeServiceId(3).price(400000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("L???t m???n ?????u ??en")
                        .description("Combo l???t mun ?????u ??en full face, ?????p m???t n???, t???y da ch???t.")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/Service/tat-ca-dich-vu/30shine-lot-mun-cam-3.jpg?v=2")
                        .typeServiceId(5).price(85000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("Ch??m s??c c???p thi???t, ultra white")
                        .description("?????p m???t n??? l???nh, t???y da ch???t s???i.")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/Service/tat-ca-dich-vu/30shine-detox-3.jpg?v=2https://storage.30shine.com/ResourceWeb/data/images/Service/tat-ca-dich-vu/mat-na-sui-bot-tay-da-chet-3.jpg?v=2")
                        .typeServiceId(5).price(50000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("G????i ??????u d??????ng sinh ??a??i Loan")
                        .description("G????i ??????u d??????ng sinh ??a??i Loan la?? ph????ng pha??p g????i ??????u nh????m l??m s???ch t??c v?? da ?????u va?? th?? gia??n v???i k??? thu???t massage, day ???n t???p trung v??o c??c huy???t ?????o gi??p h??? th???n kinh, h??? tu???n ho??n b???ch huy???t ???????c l??u th??ng, ?????c t??? ???????c ????o th???i, c?? th??? ???????c th?? gi??n v?? s???c kh???e ???????c t??ng c?????ng.")
                        .thumbnail("https://perlaspa.com.vn/wp-content/uploads/2021/01/goi-dau-duong-sinh-2.jpg")
                        .typeServiceId(2).price(300000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("G???i ?????u Detox tinh ch???t mu???i bi???n")
                        .description("S??? d???ng mu???i bi???n s??u t??? nhi??n, v?? tr??ng ????? tinh khi???t 100% gi??p l??m s???ch s??u da ?????u, s??t khu???n nh??? lo???i b??? ho??n to??n l???p d???u th???a, ph???n da ch???t tr??n da ?????u.")
                        .thumbnail("https://amoon.vn/wp-content/uploads/2020/02/dich-vu-goi-dau.png")
                        .typeServiceId(2).price(150000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("Cut Luxury")
                        .description("T?? v???n skincare, x??ng h??i m???t, c???t x??? v?? t???o ki???u t??c b???ng c??c s???n ph???m cao c???p.")
                        .thumbnail("http://luxuryman.vn/Content/image/article.jpg")
                        .typeServiceId(1).price(200000.0)
                        .build());

            }

            List<ServiceModel> services = serviceModelRepository.findAll();
        if (services.size() == 0){
            String[] link = {"https://i.pinimg.com/236x/47/ae/24/47ae2447e4cd688098398f6c8687bea0.jpg",
            "https://i.pinimg.com/236x/35/e5/a8/35e5a8cb6c8f31599b6cdff138ba13ef.jpg",
            "https://i.pinimg.com/236x/6c/93/d6/6c93d61f013b9e7ec3ea47f998574e7e.jpg",
            "https://i.pinimg.com/236x/83/4e/f6/834ef6a7e9cbd9388c3b2345af769ef3.jpg",
            "https://i.pinimg.com/236x/c3/ea/23/c3ea233f4cd95b9c6c9ee0bc0212d938.jpg"};
            Integer status = 1;
            String description = "Ki???u t??c nam,n??? ?????p ";
            String name = "?????u c???t moi";
            double[] price = {200000,300000,400000,500000};
            Integer[] type = {1,2,3,4,5};
            for (int i = 1; i < 20; i++) {
                Date dateservice = getRamdomDate(2020, 2022, "yyyy-MM-dd");
                String linkz = link[rand.nextInt(link.length)];
                double pricez = price[rand.nextInt(price.length)];
                Integer typez = type[rand.nextInt(type.length)];

                String bookingDateService = String.valueOf(dateservice.getDate());
                if (dateservice.getDate() < 10) {
                    bookingDateService = 0 + String.valueOf(dateservice.getDate());
                }

                int validateMonthService = dateservice.getMonth() + 1;
                String bookingMonthService = String.valueOf(validateMonthService);
                if (validateMonthService < 10) {
                    bookingMonthService = 0 + String.valueOf(validateMonthService);
                }
                 cal = Calendar.getInstance();
                cal.setTime(dateservice);
                String bookingYearService = String.valueOf(cal.get(Calendar.YEAR));

                 sql = "INSERT INTO services VALUES (" +
                       i + ","+
                        '"' + bookingYearService + "-" + bookingMonthService + "-" + bookingDateService + '"' + ","+
                        status + ","+
                        '"' + bookingYearService + "-" + bookingMonthService + "-" + bookingDateService + '"' + ","+
                        '"'+description+'"' + ","+
                        '"'+name+'"' + ","+
                       pricez+ ","+
                        '"'+ linkz+'"'+ ","+
                        typez+")";
                jdbcTemplate.update(sql);
            }
        }

        List<Booking> booking = bookingRepository.findAll();
        if (booking.size() == 0) {
            Integer[] branch_ids = {1, 2};
//            Integer[] emp_ids = {2, 3, 4, 5, 6, 7, 8};
            Integer[] emp_ids = {4};
            Integer[] service_names = {1,2,3,4,5,6,7,8};

            TimeBookingEnum[] time_bookings = TimeBookingEnum.values();
//            Integer[] user_ids = {5};
            Integer[] user_ids = {1,6,7,8,9};
            Integer user_ids_zero = 0;
            Integer[] statuss = {-1, 0, 1, 2};

            for (int i = 0; i < 600; i++) {
                Integer status = statuss[rand.nextInt(statuss.length)];
                Date date = getRamdomDate(2020, 2022, "yyyy-MM-dd");
                Integer branch_id = branch_ids[rand.nextInt(branch_ids.length)];
                Integer emp_id = emp_ids[rand.nextInt(emp_ids.length)];
                Integer service_name = service_names[rand.nextInt(service_names.length)];
                String time_booking = time_bookings[rand.nextInt(time_bookings.length)].timeName;
                Integer user_id = user_ids[rand.nextInt(user_ids.length)];
                String email = "Seeder@gmail.com";

                String bookingDate = String.valueOf(date.getDate());
                if (date.getDate() < 10) {
                    bookingDate = 0 + String.valueOf(date.getDate());
                }

                int validateMonth = date.getMonth() + 1;
                String bookingMonth = String.valueOf(validateMonth);
                if (validateMonth < 10) {
                    bookingMonth = 0 + String.valueOf(validateMonth);
                }
                 cal = Calendar.getInstance();
                cal.setTime(date);
                String bookingYear = String.valueOf(cal.get(Calendar.YEAR));

                 sql = "INSERT INTO bookings (id,created_at,status,updated_at,branch_id,date,date_booking,email,employee_id,phone,time_booking,name_booking,user_id) VALUES (" +
                        '"' +  "HN" +i+ '"' + ","+
                        '"' + bookingYear + "-" + bookingMonth + "-" + bookingDate + '"' + ","+
                        status + ","+
                        '"' + bookingYear + "-" + bookingMonth + "-" + bookingDate + '"' + ","+
                        branch_id + ","+
                        '"' + String.valueOf(date.getTime()) + '"'  + ","+
                        '"' +bookingDate + "-" + bookingMonth + "-" + bookingYear + '"' + ","+
                        '"' +email+ '"' + ","+
                        emp_id + ","+
                        "''" + ","+
                         '"' + time_booking + '"' + ","+
                         service_name+ ","+
                         user_id + ")"
                        ;
                jdbcTemplate.update(
                        sql);
            }
            for (int i = 601; i < 631; i++) {
                Integer status = statuss[rand.nextInt(statuss.length)];
                Date date = getRamdomDate(2021, 2022, "yyyy-MM-dd");
                Integer branch_id = branch_ids[rand.nextInt(branch_ids.length)];
                Integer service_name = service_names[rand.nextInt(service_names.length)];
                Integer emp_id = emp_ids[rand.nextInt(emp_ids.length)];

                String time_booking = time_bookings[rand.nextInt(time_bookings.length)].timeName;
                Integer user_id = user_ids_zero;
                String email = "Seeder@gmail.com";

                String bookingDate = String.valueOf(date.getDate());
                if (date.getDate() < 10) {
                    bookingDate = 0 + String.valueOf(date.getDate());
                }

                int validateMonth = date.getMonth() + 1;
                String bookingMonth = String.valueOf(validateMonth);
                if (validateMonth < 10) {
                    bookingMonth = 0 + String.valueOf(validateMonth);
                }
                cal = Calendar.getInstance();
                cal.setTime(date);
                String bookingYear = String.valueOf(cal.get(Calendar.YEAR));

                sql = "INSERT INTO bookings (id,created_at,status,updated_at,branch_id,date,date_booking,email,employee_id,phone,time_booking,name_booking,user_id) VALUES (" +
                        '"' +  "HN" +i+ '"' + ","+
                        '"' + bookingYear + "-" + bookingMonth + "-" + bookingDate + '"' + ","+
                        status + ","+
                        '"' + bookingYear + "-" + bookingMonth + "-" + bookingDate + '"' + ","+
                        branch_id + ","+
                        '"' + String.valueOf(date.getTime()) + '"'  + ","+
                        '"' +bookingDate + "-" + bookingMonth + "-" + bookingYear + '"' + ","+
                        '"' +email+ '"' + ","+
                        emp_id + ","+
                        "''" + ","+
                        '"' + time_booking + '"' + ","+
                        service_name+ ","+
                        user_id + ")"
                ;
                jdbcTemplate.update(
                        sql);
            }
        }
        List<Order> orders = orderRepository.findAll();
        if (orders.size() == 0 && booking.size()> 0) {

            Integer[] statussOrder = {-1,0,1,2};
            Integer[] customer_ids = {1, 6};
            double[] pricesOrder = {200000,300000,400000,500000};

            for (int i = 1; i < 100; i++) {
                Date dateorder = getRamdomDate(2020, 2022, "yyyy-MM-dd");
                Booking bookin =  booking.get(rand.nextInt(booking.size()));
                String booking_id = bookin.getId();
                Integer customer_id = customer_ids[rand.nextInt(customer_ids.length)];
                Double priceOrder = pricesOrder[rand.nextInt(pricesOrder.length)];
                Integer statusOrder = statussOrder[rand.nextInt(statussOrder.length)];

                String bookingDateOrder = String.valueOf(dateorder.getDate());
                if (dateorder.getDate() < 10) {
                    bookingDateOrder = 0 + String.valueOf(dateorder.getDate());
                }

                int validateMonthOrder = dateorder.getMonth() + 1;
                String bookingMonthOrder = String.valueOf(validateMonthOrder);
                if (validateMonthOrder < 10) {
                    bookingMonthOrder = 0 + String.valueOf(validateMonthOrder);
                }
                 cal = Calendar.getInstance();
                cal.setTime(dateorder);
                String bookingYearOrder = String.valueOf(cal.get(Calendar.YEAR));

                 sql = "INSERT INTO orders VALUES (" +
                        i + ","+
                        '"' + bookingYearOrder + "-" + bookingMonthOrder + "-" + bookingDateOrder + '"' + ","+
                        statusOrder + ","+
                        '"' + bookingYearOrder + "-" + bookingMonthOrder + "-" + bookingDateOrder + '"' + ","+
                        '"'+booking_id+'"' + ","+
                        customer_id + ","+
                        priceOrder + ","+ "''" +
                          ")";
                jdbcTemplate.update(
                        sql);
            }
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        if(orderDetails.size() == 0 && orders.size() > 0 && services.size() > 0){
            for (int i = 1; i < 210; i++) {
                Order order =  orders.get(rand.nextInt(orders.size()));
                Integer order_id = order.getId();
                ServiceModel serviceModel = services.get(rand.nextInt(services.size()));
               Integer service_id = serviceModel.getId();
               double unit_price = serviceModel.getPrice();
                 sql = "SELECT count(*) FROM order_details WHERE order_id = ? AND service_id = ?";

                int count = jdbcTemplate.queryForObject(sql, new Object[] { order_id,service_id }, Integer.class);
                    if (count == 0){
                        sql = "INSERT INTO order_details VALUES (" +
                                order_id + ","+
                                service_id + ","+
                                unit_price +")";
                        jdbcTemplate.update(
                                sql);
                    }
                  }
              }


        }
    }

    private void createAccount(String checkAccount){
        MembershipClass membershipClass = membershipClassRepository.findById(5).orElse(null);

        Account account = new Account();
        String name;
        String email;
        String phone;
        String description;
        String roleName;
        switch (checkAccount) {
                case "ADMIN":
                name = "Admin";
                roleName = "ADMIN";
                description = "Qu???n tr??? vi??n ";
                email = "admin@gmail.com";
                phone = "0123523532";
                break;
            case "RECEPTIONISTS":
                name = "Receptionists";
                roleName = "RECEPTIONISTS";
                description = "L??? t??n";
                email = "receptionists@gmail.com";
                phone = "43241414141532";
                break;
            case "STAFF1":
                name = "Staff1";
                roleName = "STAFF";
                description = "Tr?????c ????y, m???i ng?????i th?????ng ngh?? ????n ??ng kh??ng c???n qu?? quan tr???ng ngo???i h??nh, xu??? xo?? l?? m???t n??t ?????c tr??ng c???a ????n ??ng. Nh??ng ??? th???i ?????i b??y gi??? th?? m???i th??? kh??c r???i. Kh??ng ch??? ph??? n??? m?? ????n ??ng c??ng n??n ch??m ch??t ngo???i h??nh. Ngo???i h??nh l?? th??? ???nh h?????ng r???t nhi???u t???i c??c m???i quan h??? v?? c??ng vi???c, cu???c s???ng v?? k??? t??? khi m??nh nh???n ra ??i???u ???? th?? m??nh d???n quan t??m ch??m s??c ngo???i h??nh b???n th??n nhi???u h??n.B???n sinh ra ???? ?????p th?? l?? m???t ??i???u t???t, nh??ng n???u nh?? m??nh ch??a c?? th?? kh??ng c?? ngh??a m??nh m??i m??i kh??ng c?? ???????c. C?? r???t nhi???u c??ch ????? khi???n m??nh tr??? n??n ?????p h??n nh?? t???p gym, th???i trang v?? ki???u t??c";
                email = "staff1@gmail.com";
                phone = "0214124142";
                break;
            case "STAFF2":
                name = "Staff2";
                roleName = "STAFF";
                description = "Ngh??? thu???t kh??ng bao gi??? c?? ??i???m d???ng. M??nh hi???u r???t r?? ??i???u n??y v?? lu??n t??? m??? trong t???ng ???????ng k??o.V???i tay ngh??? chu???n qu???c t???, c??ng v???i s??? am hi???u v??? m??i t??c, con ng?????i Vi???t, ch???c ch???n c??c m??nh s??? c???t cho b???n ki???u t??c ??ng ?? nh???t.Kh??ng nh???ng th???, c???t Sport ??? ti???m, b???n c??n ???????c vu???t s??p t???o ki???u mi???n ph?? kh??ng n??i n??o c??, h?????ng d???n vu???t t???i nh?? nhanh, d??? d??ng v?? ?????p nh???t.";
                email = "staff2@gmail.com";
                phone = "0214124143";
                break;
            case "STAFF3":
                name = "Staff3";
                roleName = "STAFF";
                description = "T??i th???y c?? ngo???i h??nh ?????p s??ng s???a s??? gi??p ????n ??ng t??? tin h??n r???t nhi???u. Nam gi???i Vi???t Nam ?????p trai ch???, nh??ng ch??a ???????c ?????nh h??nh phong c??ch, ch??a c?? ng?????i t?? v???n, d???n d???t xu h?????ng. Th???m ch?? r???t hi???m lu??n. T??i r???t khao kh??t gi??p ae tr??? th??nh phi??n b???n t???t h??n, n??n c?? ?????ng l???c ??i t??m trend t??c, t??m phong c??ch cho ae nam gi???i Vi???t. V?? v???y m?? t??i ???? th??? r???t nhi???u ki???u t??c, m??u t??c, thu n???p ??c r???t nhi???u ki???n th???c v?? ???? c?? t??i c???a ng??y h??m nay." +
                        "H??nh tr??nh ????? ??i t??m nh???ng Trend t??c ??ang l?? xu h?????ng, nh???ng Trend t??c m???i cho ae ph???i n??i l?? c??ng g???p kh?? nhi???u gian nan, kh?? kh??n b???i m???t ch??t l?? l?? th??i th?? c??i Trend ???? ???? ngu???i hay l?? gi???m ????? n??ng ????? hot r???i. N??n b???t bu???c b???n ph???i c???c k?? nh???y c???m. T??i c??n nh??? trend t??c con s??u t??? H??n Qu??c tr??n v??? VN, t??i v?? anh em stylist t???i th???c c??? ????m ????? nghi??n c???u k??? thu???t, l??m sao v???n gi??? ???????c n??t ?????c tr??ng c???a ki???u t??c m?? v???n ph?? h???p v???i khu??n m???t, ch???t t??c nam gi???i Vi???t. C?? nh???ng l??c th??? ??i th??? l???i v???n ch??a ??ng ?? c??n c??i nhau to, nh??ng khi xong th?? m???y ae vui l???m,...";
                email = "staff3@gmail.com";
                phone = "0214124256";
                break;
            case "STAFF4":
                name = "Staff4";
                roleName = "STAFF";
                description = "Vi???c thay ?????i ki???u t??c gi??p t??i nhanh t??m ???????c phi??n b???n ho??n thi???n nh???t c???a m??nh, nh??? v???y m?? t??i c??ng nh???y c???m h??n trong vi???c b???t Trend. t??i ???? t???y t??c kho???ng m???y ch???c l???n r???i c??n nhu???m th?? r???t nhi???u l???n ch??? t??i c??ng kh??ng nh??? r?? ????? ????a ra con s??? c??? th???. M???c ????ch l?? ????? v???a tr???i nghi???m, v???a th??? ki???u t??c, m??u t??c m???i v?? show ra cho anh em tham kh???o." +
                        "Ko ph???i ????n thu???n l?? ki???u t??c n??y n?? nhi???u m??u, ki???u n??y xo??n t??t l??n th?? l?? Trend, m?? Trend l?? nh???ng ki???u t??c ??? th???i ??i???m ???? ??ang th???c s??? ???????c nhi???u ae ch?? ?? t???i, nhi???u ae ????? c??i ki???u t??c ???y, l??? m???t, ?????c ????o nh??ng ???ng d???ng cao, ph?? h???p v???i ??a s??? anh em nam gi???i.\n" +
                        "V?? d??? nh?? ki???u t??c Mohican ????nh ????m n??m ngo??i hay g???n ????y nh???t l?? ki???u t??c u???n con s??u. Ki???u t??c n??y c?? th??? hot ??? th??? gi???i, t??y ????? r???t ?????p nh??ng v???i g????ng m???t, ch???t t??c c???a ng?????i Vi???t ch??a ch???c ???? h???p n??n t??i v?? c??c chuy??n gia t??c ??? ti???m ph???i nghi??n c???u r???t nhi???u, th??? nghi???m tr??n ch??nh m??i t??c c???a t??i v?? c??c anh em ?????n khi n??o ra chu???n th?? th??i.\n" +
                        "Tuy kh?? kh??n nh??ng l??m v?? ??am m?? v?? ae th??i, n??n t??i c??ng ???? theo ??u???i c??ng t??? r???t l??u r???i. Quan tr???ng l?? anh em ?????p h??n, anh em c?? ch??? d???a tin t?????ng ????? lu??n lu??n b???t k???p nh???ng xu h?????ng t??c m???i. Ko bao gi??? s??? l???i th???i.";
                email = "staff4@gmail.com";
                phone = "0214124589";
                break;
            case "CUSTOMER_CARE":
                name = "Customer Care";
                roleName = "CUSTOMER_CARE";
                description = "Nh??n vi??n ch??m s??c";
                email = "customer_care@gmail.com";
                phone = "543564312";
                break;
            case "CUSTOMER":
                name = "Customer";
                roleName = "CUSTOMER";
                description = "Kh??ch h??ng";
                email = "customer@gmail.com";
                phone = "464314141";
                break;
            default:
                name = "";
                roleName = "CUSTOMER";
                description = "";
                email = "";
                phone = "";
                break;
                }
        Set<Role> roles = new HashSet<>();
        Role role = roleService.getRole(roleName);
        roles.add(role);

        account.setName(name);
        account.setDescription(description);
        account.setEmail(email);
        account.setAddress("From No Where");
        account.setPhone(phone);
        account.setPassword(passwordEncoder.encode("12345678"));
        account.setGender(GenderEnum.MALE.gender);
        account.setMembershipClass(membershipClass);
        account.setRoles(roles);
        account.setCreated_at(new Date());
        account.setStatus(StatusEnum.ACTIVE.status);
        accountRepository.save(account);
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public Date getRamdomDate(int yearFrom, int yearEnd, String format) throws ParseException {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(yearFrom, yearEnd);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        String created_at = gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);
        //"yyyy-MM-dd"
        return new SimpleDateFormat(format).parse(created_at);
    }
}

package com.submission;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@SpringBootApplication
public class Coupons {
    public static void main(String[] args) {
        SpringApplication.run(Coupons.class, args);
    }
}

@RestController
class CouponController {
    @Autowired
    private CouponService service;

    @PostMapping("/coupons")
    public ResponseEntity<Coupon> create(@RequestBody Coupon c) {
        return ResponseEntity.ok(service.createCoupon(c));
    }

    @GetMapping("/coupons")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.getAllCoupons());
    }

    @PostMapping("/applicable-coupons")
    public ResponseEntity<?> best(@RequestBody CouponRequest r) {
        Map<String, Object> res = service.findBestCoupon(r.getUser(), r.getCart());
        if (res == null) return ResponseEntity.ok(Map.of("message", "No applicable coupon found"));
        return ResponseEntity.ok(res);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> c) {
        if ("hire-me@anshumat.org".equals(c.get("email")) && "HireMe@2025!".equals(c.get("password"))) {
            return ResponseEntity.ok(Map.of("status", "success", "token", "dummy-token-123"));
        }
        return ResponseEntity.status(401).body(Map.of("status", "failure", "message", "Invalid credentials"));
    }
}

@Service
class CouponService {
    private final Map<String, Coupon> couponStore = new ConcurrentHashMap<>();

    public Coupon createCoupon(Coupon coupon) {
        couponStore.put(coupon.getCode(), coupon);
        return coupon;
    }

    public List<Coupon> getAllCoupons() {
        return new ArrayList<>(couponStore.values());
    }

    public Map<String, Object> findBestCoupon(UserContext user, Cart cart) {
        LocalDate today = LocalDate.now();
        double cartTotal = cart.getItems().stream().mapToDouble(i -> i.getUnitPrice() * i.getQuantity()).sum();

        List<Coupon> eligibleCoupons = couponStore.values().stream()
                .filter(c -> !today.isBefore(c.getStartDate()) && !today.isAfter(c.getEndDate()))
                .filter(c -> isEligible(c, user, cart, cartTotal))
                .collect(Collectors.toList());

        if (eligibleCoupons.isEmpty()) return null;

        eligibleCoupons.sort((c1, c2) -> {
            double d1 = calculateDiscount(c1, cartTotal);
            double d2 = calculateDiscount(c2, cartTotal);
            if (d2 != d1) return Double.compare(d2, d1);
            return c1.getCode().compareTo(c2.getCode());
        });

        Coupon best = eligibleCoupons.get(0);
        double discount = calculateDiscount(best, cartTotal);
        Map<String, Object> response = new HashMap<>();
        response.put("coupon", best.getCode());
        response.put("discount", discount);
        response.put("finalPrice", cartTotal - discount);
        return response;
    }

    private double calculateDiscount(Coupon c, double total) {
        if ("FLAT".equalsIgnoreCase(c.getDiscountType())) return c.getDiscountValue();
        if ("PERCENT".equalsIgnoreCase(c.getDiscountType())) {
            double d = (c.getDiscountValue() / 100.0) * total;
            return (c.getMaxDiscountAmount() != null) ? Math.min(d, c.getMaxDiscountAmount()) : d;
        }
        return 0.0;
    }

    private boolean isEligible(Coupon c, UserContext u, Cart cart, double total) {
        Eligibility e = c.getEligibility();
        if (e == null) return true;
        if (e.getAllowedUserTiers() != null && !e.getAllowedUserTiers().contains(u.getUserTier())) return false;
        if (e.getMinCartValue() != null && total < e.getMinCartValue()) return false;
        return true;
    }
}

@Data
class Coupon {
    private String code;
    private String description;
    private String discountType;
    private Double discountValue;
    private Double maxDiscountAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer usageLimitPerUser;
    private Eligibility eligibility;
}

@Data
class Eligibility {
    private List<String> allowedUserTiers;
    private Double minLifetimeSpend;
    private Integer minOrdersPlaced;
    private Boolean firstOrderOnly;
    private List<String> allowedCountries;
    private Double minCartValue;
    private List<String> applicableCategories;
    private List<String> excludedCategories;
    private Integer minItemsCount;
}

@Data
class Cart { private List<CartItem> items; }

@Data
class CartItem {
    private String productId;
    private String category;
    private Double unitPrice;
    private Integer quantity;
}

@Data
class UserContext {
    private String userId;
    private String userTier;
    private String country;
    private Double lifetimeSpend;
    private Integer ordersPlaced;
}

@Data
class CouponRequest { private Cart cart; private UserContext user; }


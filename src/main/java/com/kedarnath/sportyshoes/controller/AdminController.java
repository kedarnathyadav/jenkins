package com.kedarnath.sportyshoes.controller;

import com.kedarnath.sportyshoes.dto.ProductDTO;
import com.kedarnath.sportyshoes.dto.PurchaseDTO;
import com.kedarnath.sportyshoes.global.GlobalData;
import com.kedarnath.sportyshoes.model.Category;
import com.kedarnath.sportyshoes.model.Product;
import com.kedarnath.sportyshoes.model.Purchase;
import com.kedarnath.sportyshoes.repository.PurchaseRepository;
import com.kedarnath.sportyshoes.service.CategoryService;
import com.kedarnath.sportyshoes.service.ProductService;
import com.kedarnath.sportyshoes.service.PurchaseService;
import com.kedarnath.sportyshoes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

@Controller
public class AdminController {

    public String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    PurchaseRepository purchaseRepository;



    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories",categoryService.getAllCategory());
        return "categories";
    }
    @GetMapping("/admin/categories/add")
    public String getCatAdd( Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }
    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
              categoryService.addCategory(category);
               return "redirect:/admin/categories";
    }
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";

    }
    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model){
        Optional<Category> category =categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }else
            return "404";

    }
    //product section
    @GetMapping("/admin/products")
    public String products( Model model,  String keyword){
        if(keyword!=null) {
            String newkeyword = "%"+keyword.toLowerCase()+"%";
            model.addAttribute("products", productService.findByKeyword(newkeyword));
        }
        else {
            model.addAttribute("products", productService.getAllProduct());
        }
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String productAddGet( Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("productDTO") ProductDTO productDTO, @RequestParam("productImage")MultipartFile file, @RequestParam ("imgName")String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else{
            imageUUID = imgName;

        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }
    @GetMapping("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable long id, Model model){
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId((product.getCategory().getId()));
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO",productDTO);


        return "productsAdd";

    }

    @GetMapping("/admin/users")
    public String userslist( Model model,  String keyword){
        if(keyword!=null) {
            String newkeyword = "%"+keyword.toLowerCase()+"%";
            model.addAttribute("users", userService.findByKeyword(newkeyword));
        }
        else {
            model.addAttribute("users", userService.getAllUser());
        }
        return "users";
    }

    @RequestMapping(value = "/admin/payNow", method = RequestMethod.GET)
    public String purchaseGet( @ModelAttribute("purchaseDTO")PurchaseDTO loginModel, Model model){
//        model.addAttribute("purchaseDTO",new PurchaseDTO());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart",GlobalData.cart);
        return "checkout";
    }
    @PostMapping("/admin/payNow")
    public String purchasePost(@ModelAttribute("purchaseDTO") PurchaseDTO purchaseDTO, @RequestParam ("imgName")String imgName) throws IOException {
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        Purchase purchase = new Purchase();
        purchase.setPurchase_id(purchaseDTO.getPurchase_id());
        purchase.setFirstname(purchaseDTO.getFirstname());
        purchase.setLastname(purchaseDTO.getLastname());
        purchase.setProduct_name(purchaseDTO.getProduct_name());
        purchase.setPurchase_date(formattedDate);
        purchase.setAddress_1(purchaseDTO.getAddress_1());
        purchase.setAddress_2(purchaseDTO.getAddress_2());
        purchase.setPhoneno(purchaseDTO.getPhoneno());
        purchase.setEmail(purchaseDTO.getEmail());
        purchase.setPostalcode(purchaseDTO.getPostalcode());
        purchase.setCity(purchaseDTO.getCity());
        purchase.setProduct_categoryName(purchaseDTO.getProduct_categoryName());
        purchase.setProduct_imageName(purchaseDTO.getProduct_imageName());
        purchaseRepository.save(purchase);
        return "redirect:/payNow";
    }
    @GetMapping("/admin/purchases")
    public String purchases( Model model,  String keyword){
        if(keyword!=null) {
            String newkeyword = "%"+keyword.toLowerCase()+"%";
            model.addAttribute("products", purchaseService.findByKeyword(newkeyword));
        }
        else {
            model.addAttribute("purchases", purchaseService.getAllPurchases());
        }
        return "purchases";
    }
}














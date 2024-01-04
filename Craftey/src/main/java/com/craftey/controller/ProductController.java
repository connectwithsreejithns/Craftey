package com.craftey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.craftey.model.Category;
import com.craftey.model.Product;
import com.craftey.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class ProductController {
	@Autowired
	ProductService productService;

	@GetMapping("/home")
	public String showAdminHome(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "Admin/index";
	}

	/*
	 * @GetMapping("/Admin/product") public String showAddProduct(Model model) {
	 * 
	 * model.addAttribute("category", new Category());
	 * model.addAttribute("categories", productService.getAllCategories());
	 * model.addAttribute("products", productService.getAllProduct());
	 * 
	 * return "Admin/product"; }
	 */

	@GetMapping("/addProduct")
	public String showAddproduct() {
		return "Admin/addProduct";
	}

	@PostMapping("/productEntry")
	public String saveproduct(@RequestParam("file") MultipartFile file, @RequestParam("pname") String name,
			@RequestParam("price") int price, @RequestParam("desc") String desc) {

		productService.saveProductToDB(file, name, desc, price);
		return "redirect:/admin/home";

	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
		return "redirect:/admin/home";
	}

	@PostMapping("/changeName")
	public String changePname(@RequestParam("id") Long id, @RequestParam("newPname") String name) {
		productService.changeProductName(id, name);
		return "redirect:/admin/home";
	}

	@PostMapping("/changeDescription")
	public String changeDescription(@RequestParam("id") Long id, @RequestParam("newDescription") String desc) {
		productService.changeProductDescription(id, desc);
		return "redirect:/admin/home";
	}

	@PostMapping("/changePrice")
	public String changePrice(@RequestParam("id") Long id, @RequestParam("newPrice") double price) {
		productService.changeProductPrice(id, price);
		return "redirect:/admin/home";
	}

}

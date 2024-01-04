package com.craftey.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.craftey.model.Category;
import com.craftey.model.Product;
import com.craftey.repository.CategoryRepository;
import com.craftey.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}
	
	public Optional<Product> getProduct(Long id) {
		return productRepo.findById(id);
	}

	public void saveProductToDB(MultipartFile file, String name, String description, int price) {
		Product product = new Product();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a a valid file");
		}
		try {
			product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		product.setDesc(description);
		product.setName(name);
		product.setPrice(price);
		productRepo.save(product);
	}

	public void changeProductName(Long id, String name) {
		Product p = new Product();
		p = productRepo.findById(id).get();
		p.setName(name);
		productRepo.save(p);
	}

	public void changeProductDescription(Long id, String description) {
		Product p = new Product();
		p = productRepo.findById(id).get();
		p.setDesc(description);
		productRepo.save(p);
	}

	public void changeProductPrice(Long id, Double price) {
		Product p = new Product();
		p = productRepo.findById(id).get();
		p.setPrice(price);
		productRepo.save(p);
	}
	
	
	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}

	public Category saveCategory(Category category) {
		return categoryRepo.save(category);
	}
}

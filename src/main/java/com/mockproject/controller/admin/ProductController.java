package com.mockproject.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mockproject.entity.ProductTypes;
import com.mockproject.entity.Products;
import com.mockproject.entity.UnitTypes;
import com.mockproject.service.ProductTypesService;
import com.mockproject.service.ProductsService;
import com.mockproject.service.UnitTypesService;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private ProductTypesService productTypesService;

	@Autowired
	private UnitTypesService unitTypesService;

	@GetMapping("")
	public String doGetProduct(Model model) {
		List<Products> products = productsService.findAll();
		model.addAttribute("products", products);

		List<Products> productss = productsService.findProducts();
		model.addAttribute("productss", productss);

		model.addAttribute("productRequest", new Products());
		
		List<ProductTypes> productTypes = productTypesService.findAll();
		model.addAttribute("productTypes", productTypes);
		
		List<UnitTypes> unitTypes = unitTypesService.findAll();
		model.addAttribute("unitTypes", unitTypes);
		return "admin/product";

	}

	@GetMapping("/delete")
	public String doGetDeleted(@RequestParam("id") Long id, RedirectAttributes ra) {
			try {
				productsService.deleteLogical(id);
				ra.addFlashAttribute("succeedMessage", "ProductId :" + id + "Was deleted");
				System.out.println(id);
			} catch (Exception e) {
				e.printStackTrace();
				ra.addFlashAttribute("errorMessage", "Cannot deleted products" + id);
			}
		return "redirect:/admin/product";
	}

	@GetMapping("/repeat")
	public String doGetRepeat(@RequestParam("id") Long id, RedirectAttributes ra) {
		try {
			productsService.repeatLogical(id);
			ra.addFlashAttribute("succeedMessage", "Productid :" + id + "Was Repeat");
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("errorMessage", "Cannot Repeat productId :" + id);
		}
		return "redirect:/admin/product";
	}
	
	@GetMapping("/edit")
	public String doGetEditProducts(@RequestParam("id")Long id, Model model) {
		Products productRequest = productsService.findById(id);
		model.addAttribute("productRequest", productRequest);
		
		List<ProductTypes> productTypes = productTypesService.findAll();
		model.addAttribute("productTypes", productTypes);
		
		List<UnitTypes> unitTypes = unitTypesService.findAll();
		model.addAttribute("unitTypes", unitTypes);
		
		return "admin/product::#form";
	}
	
	@PostMapping("/create")
	public String doPostCreate(@ModelAttribute("productRequest") Products productRequest, RedirectAttributes ra, Model model) {
		try { 
			productsService.insert(productRequest);
			ra.addFlashAttribute("succeedMessage", "Product " + productRequest.getName() + " Was created successfully");
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("errorMessage", "Cannot create product :" + productRequest.getName());
		}
		return "redirect:/admin/product";
	}
	
	@PostMapping("/edit")
	public String doPostUpdate(@Valid @ModelAttribute("productRequest") Products productRequest, BindingResult bindingResult,
			RedirectAttributes redirectAttribute, Model model) {
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			redirectAttribute.addFlashAttribute("errorMessage", "Product is not valid");
		} else {
			try {
				List<ProductTypes> productTypes = productTypesService.findAll();
				model.addAttribute("productTypes", productTypes);
				
				List<UnitTypes> unitTypes = unitTypesService.findAll();
				model.addAttribute("unitTypes", unitTypes);
				productsService.update(productRequest);
				redirectAttribute.addFlashAttribute("succeedMessage",
						"Product " + productRequest.getId() + " has been edited successfully");
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttribute.addFlashAttribute("errorMessage",
						"Cannot update Product : " + productRequest.getId());
			}
		}
		return "redirect:/admin/product";
	}
}

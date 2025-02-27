package com.fpoly.java5.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.java5.beans.ProductBean;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.entity.ImageEntity;
import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.jpas.CategoryJPA;
import com.fpoly.java5.jpas.ImagesJPA;
import com.fpoly.java5.jpas.ProductJPA;

@Service
public class ProductService {
	@Autowired
	ImagesJPA imageJPA;

	@Autowired
	CategoryJPA categoryJPA;

	@Autowired
	ProductJPA productJPA;

	@Autowired
	ImageService imageService;

	public boolean saveProduct(ProductBean productBean) {
        try {
            ProductEntity product = new ProductEntity();
            product.setName(productBean.getName());
            product.setDescription(productBean.getDesc());
            product.setPrice(productBean.getPrice());
            product.setQuantity(productBean.getQuantitty());
            product.setActive(true);
            product.setDateCreated();

            Optional<CategoryEntity> cOptional = categoryJPA.findById(productBean.getCat_id());
            product.setCategory(cOptional.orElse(null));

            ProductEntity productSave = productJPA.save(product); // Lưu sản phẩm để có ID

            // Lưu hình ảnh
            for (MultipartFile file : productBean.getImages()) {
                String fileName = imageService.saveImage(file);
                ImageEntity image = new ImageEntity();
                image.setImageName(fileName);
                image.setProduct(productSave);
                imageJPA.save(image);
            }  

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

	public boolean updateProduct(int productId, ProductBean productBean) {
	    try {
	        Optional<ProductEntity> optionalProduct = productJPA.findById(productId);
	        if (optionalProduct.isPresent()) {
	            ProductEntity product = optionalProduct.get();	            
	            // Cập nhật thông tin cơ bản
	            product.setName(productBean.getName());
	            product.setDescription(productBean.getDesc());
	            product.setPrice(productBean.getPrice());
	            product.setQuantity(productBean.getQuantitty());	            
	            // Cập nhật category
	            categoryJPA.findById(productBean.getCat_id()).ifPresent(product::setCategory);   
	            
	            ProductEntity productSave = productJPA.save(product); 
	            // Xử lý ảnh
	            if (productBean.getImages() != null 
	                && !productBean.getImages().isEmpty() 
	                && !productBean.getImages().get(0).isEmpty()) {
	                
	                // Xóa ảnh cũ
	                imageJPA.deleteByProId(optionalProduct.get().getId());
	                
	                // Thêm ảnh mới
	                for (MultipartFile file : productBean.getImages()) {
	                    if (!file.isEmpty()) { // Kiểm tra file không rỗng
	                        String fileName = imageService.saveImage(file);
	                        ImageEntity image = new ImageEntity();
	                        image.setImageName(fileName);
	                        image.setProduct(productSave);
	                        imageJPA.save(image);
	                        System.out.println("Số lượng ảnh nhận được: " + productBean.getImages().size());        
	                    }
	                }
	            }
	            
	            return true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean deleteProduct(int productId) {
	    try {
	        Optional<ProductEntity> optionalProduct = productJPA.findById(productId);
	     
	        if (optionalProduct.isPresent()) {
	            // Xóa ảnh trước
	            imageJPA.deleteByProId(productId);
	            
	            // Xóa sản phẩm
	            productJPA.delete(optionalProduct.get());
	            
	            // Đảm bảo xóa thành công
	            return !productJPA.existsById(productId); 
	        }    
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public List<ProductEntity> searchProductsByName(String name) {
        return productJPA.findByName(name);}
	
}

package com.fpoly.java5.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fpoly.java5.beans.UserBean;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.UserJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {
	@Autowired
	UserJPA userJPA;

	@Autowired
	ImageService imageService;

	@Autowired
	HttpServletResponse resp;

	public List<UserEntity> searchUsers(String keyword, boolean asc) {
	    Sort sort = asc ? Sort.by(Sort.Direction.ASC, "username") : Sort.by(Sort.Direction.DESC, "username");
	    return userJPA.searchUsers(keyword, sort);
	}

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	public String insertUser(UserBean userBean) {
		try {
			// Kiểm tra username và email có tồn tại chưa
			List<UserEntity> userEntities = userJPA.findByUsernameAndEmail(userBean.getUsername(), userBean.getEmail());
			if (!userEntities.isEmpty()) {
				return "Username và email đã tồn tại";
			}

			// Lưu file vào project => fileName
			String fileName = imageService.saveImage(userBean.getAvatar());
			if (fileName == null) {
				return "Error image";
			}

			// Mã hóa mật khẩu với số vòng băm cao hơn
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
			String hashedPassword = passwordEncoder.encode(userBean.getPassword());

			// Chuyển đổi bean thành entity
			UserEntity userEntity = new UserEntity();
			userEntity.setUsername(userBean.getUsername());
			userEntity.setPassword(hashedPassword); // Lưu mật khẩu đã mã hóa
			userEntity.setName(userBean.getName());
			userEntity.setEmail(userBean.getEmail());
			userEntity.setPhone(userBean.getPhone());
			userEntity.setAvatar(fileName);
			userEntity.setActive(true);
			userEntity.setRole(1);

			System.out.println("password bcript: " + hashedPassword);

			userJPA.save(userEntity);
		} catch (Exception e) {
			return "Có lỗi trong quá trình thêm user";
		}
		return null;
	}

	public UserEntity authenticateAndSetCookies(String username, String password) {
		Optional<UserEntity> userOptional = userJPA.findByUsername(username);

		if (userOptional.isPresent()) {
			UserEntity userEntity = userOptional.get();

			if (passwordEncoder.matches(password, userEntity.getPassword())) {
				setUserCookies(userEntity);
				return userEntity;
			} else {
				throw new RuntimeException("Thông tin đăng nhập không chính xác");
			}
		} else {
			throw new RuntimeException("Thông tin đăng nhập không chính xác");
		}
	}

	private void setUserCookies(UserEntity userEntity) {
		Cookie cookieUserId = new Cookie("user_id", String.valueOf(userEntity.getId()));
		cookieUserId.setMaxAge(10 * 60 * 60);
		cookieUserId.setPath("/");
		resp.addCookie(cookieUserId);

		Cookie cookieUserRole = new Cookie("role", String.valueOf(userEntity.getRole()));
		cookieUserRole.setMaxAge(10 * 60 * 60);
		cookieUserRole.setPath("/");
		resp.addCookie(cookieUserRole);

	}

	public boolean deleteUser(int id) {
		try {
			Optional<UserEntity> user = userJPA.findById(id);
			if (!user.isPresent()) {
				return false;
			}

			userJPA.delete(user.get());

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public String updateUser(UserBean userBean) {
		try {

			List<UserEntity> users = userJPA.findByUsernameOrEmailAndId(userBean.getUsername(), userBean.getEmail(),
					userBean.getId().get());

			if (users.size() > 0) {
				return "Username hoặc email đã tồn tại";
			}

			String fileName = imageService.saveImage(userBean.getAvatar());

			if (fileName == null) {
				return "imgae err";
			}
			UserEntity userEntity = new UserEntity();
			userEntity.setId(userBean.getId().get());
			userEntity.setUsername(userBean.getUsername());
			userEntity.setPassword(userBean.getPassword());
			userEntity.setName(userBean.getName());
			userEntity.setEmail(userBean.getEmail());
			userEntity.setPhone(userBean.getPhone());
			userEntity.setActive(true);
			userEntity.setRole(1);
			userEntity.setAvatar(fileName);

			userJPA.save(userEntity);

		} catch (Exception e) {
			return "Có lỗi khi cập nhật user";
		}

		return null;
	}
	
	public void updateUserStatus(Integer id, boolean active) {
	    userJPA.findById(id).ifPresent(user -> {
	        user.setActive(active);
	        userJPA.save(user);
	    });
	}

}

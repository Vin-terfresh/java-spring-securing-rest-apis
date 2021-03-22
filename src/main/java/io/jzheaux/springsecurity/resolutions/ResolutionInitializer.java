package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {

	private final ResolutionRepository resolutions;
	private final UserRepository userRepository;

	public ResolutionInitializer(ResolutionRepository resolutions, UserRepository userRepository) {
		this.resolutions = resolutions;
		this.userRepository = userRepository;
	}

	@Override
	public void afterSingletonsInstantiated() {
		User user = new User("user", "{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
		user.grantAuthority("resolution:read");
		user.grantAuthority("resolution:write");
		user.grantAuthority("user:read");
		user.setFullName("User Userson");
		userRepository.save(user);

		User hasread = new User("hasread", "{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
		hasread.grantAuthority("resolution:read");
		hasread.grantAuthority("user:read");
		hasread.setFullName("Has Read");
		userRepository.save(hasread);

		User haswrite = new User("haswrite", "{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
		haswrite.grantAuthority("resolution:write");
		haswrite.setFullName("Has Write");
		userRepository.save(haswrite);

		User admin = new User("admin","{bcrypt}$2a$10$bTu5ilpT4YILX8dOWM/05efJnoSlX4ElNnjhNopL9aPoRyUgvXAYa");
		admin.grantAuthority("ROLE_ADMIN");
		admin.grantAuthority("user:read");
		admin.setFullName("Admin Adminson");
		userRepository.save(admin);

		resolutions.save(new Resolution("Read War and Peace", "user"));
		resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
		resolutions.save(new Resolution("Hang Christmas Lights", "user"));
	}
}

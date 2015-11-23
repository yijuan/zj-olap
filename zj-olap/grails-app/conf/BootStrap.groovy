import com.surelution.zjolap.User;
import com.surelution.zjolap.Role;
import com.surelution.zjolap.UserRole;

class BootStrap {

	def init = { servletContext ->
		def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
		def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

		def admin = User.findByUsername('admin')
		if(!admin) {
			admin = new User(username: 'admin', password: 'admin')
		}
		admin.save(flush: true)

		UserRole.create admin, adminRole, true
	}
    def destroy = {
    }
}

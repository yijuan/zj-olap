package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class UserController {

    static allowedMethods = [save: "POST"]

	def springSecurityService
	
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create() {
        [userInstance: new User(params)]
    }

    def save() {
        def userInstance = new User(params)
		userInstance.passwordExpired = false
		userInstance.accountExpired = false
		userInstance.accountLocked = false
		userInstance.enabled = true
		def branchId = params.long('branch.id')
		def branch = Branch.get(branchId)
		userInstance.branch = branch
		
        if (!userInstance.save(flush: true)) {
            //render(view: "create", model: [userInstance: userInstance])
			flash.message="用户创建失败，请重新创建！"
			redirect(action: "list")
            return
        }
		
		
		def branchRole = BranchRole.findByBranch(branch)
		if(branchRole) {
			UserRole.create(userInstance,branchRole.role,true)
		}

		/*def userBranch = new UserBranch()
		userBranch.user = userInstance
		userBranch.branch = branch*/

        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
       // redirect(action: "show", id: userInstance.id)
		redirect(action: "list")
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }
		
		def branch = UserBranch.findByUser(userInstance)

        [userInstance: userInstance, branch:branch?.branch]
    }

    def edit(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }
		
		def ub = UserBranch.findByUser(userInstance)

		[userInstance: userInstance, branch:ub?.branch]
    }

    def update(Long id, Long version) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list",params:[max:params.max,offset:params.offset])
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
               // render(view: "edit", model: [userInstance: userInstance])
				redirect(action: "list",params:[max:params.max,offset:params.offset])
                return
            }
        }

//        userInstance.properties = params
		userInstance.username = params.username
		if(params.password) {
			userInstance.password = params.password
		}
		def branchId = params["branch.id"]
		def branch = Branch.get(branchId)
		/*if(branch) {
			def ub = UserBranch.findByUser(userInstance)
			if(ub) {
				ub.branch = branch
			} else {
				ub = new UserBranch()
				ub.user = userInstance
				ub.branch = branch
			}
			ub.save(flush:true)
		}*/

        if (!userInstance.save(flush: true)) {
            //render(view: "edit", model: [userInstance: userInstance])
			redirect(action: "list",params:[max:params.max,offset:params.offset])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
      //  redirect(action: "show", id: userInstance.id)
		redirect(action: "list",params:[max:params.max,offset:params.offset])
    }

    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        try {
			def userRoles =  UserRole.findAllByUser(userInstance)
			if(userRoles) {
				userRoles.each {
					it.delete(flush: true);
				}
			}
			
            userInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
          //  redirect(action: "show", id: id)
			redirect(action: "list")
        }
    }
	
	def changePw() {
		def newPw1 = params.newPw1
		def newPw2 = params.newPw2
		if(newPw1 != newPw2) {
			flash.message = message(code: 'password.and.confirmPassword.not.match')
			redirect(action:'showChangePw')
			return
		}
		def user = springSecurityService.currentUser
		user = User.findByUsername(user.username)
		if(user) {
//			def pw1 = params.password // the old password which user input
//			def pw2 = springSecurityService.encodePassword(pw1, user.username) // the encrypted old password user input
//			def pw3 = user.password
//			println "${pw1},\n ${pw2},\n ${pw3}"
			
			if(springSecurityService.passwordEncoder.isPasswordValid(user.password, params.password, null)) {
				user.password = newPw1
				user.save(flush:true)
				flash.message = message(code: 'password.change.succeed')
				redirect(action:'showChangePw')
				return
			} else {
				flash.message = message(code: 'old.password.not.match')
				redirect(action:'showChangePw')
				return
			}
		}
	}
	
	
	def showChangePw() {
	
	}
}

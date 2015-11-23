package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class CategoryController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [categoryInstanceList: Category.list(params), categoryInstanceTotal: Category.count()]
    }

    def create() {
        [categoryInstance: new Category(params)]
    }
	
	def nameValidate(category) {
		if(!category?.name) {
			return "名称不能为空";
		}
		
		def  br = Category.findByName(category?.name)
			
		if(category?.id) {
			if(category?.id == br?.id) {
				return "";
			}
		}
		if(br) {
			return "名称不能重复";
		}
	}
	
    def save() {
        def categoryInstance = new Category(params)
		
		def msg = nameValidate(categoryInstance);
		if(msg) {
			flash.message = msg;
			//render(view: "create", model: [categoryInstance: categoryInstance])
			redirect(action:'list')
			return
		}

        if (!categoryInstance.save(flush: true)) {
           // render(view: "create", model: [categoryInstance: categoryInstance])
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
       // redirect(action: "show", id: categoryInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        [categoryInstance: categoryInstance]
    }

    def edit(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        [categoryInstance: categoryInstance]
    }

    def update(Long id, Long version) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (categoryInstance.version > version) {
                categoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'category.label', default: 'Category')] as Object[],
                          "Another user has updated this Category while you were editing")
                //render(view: "edit", model: [categoryInstance: categoryInstance])
				redirect(action:'list')
                return
            }
        }

        categoryInstance.properties = params
		
		
		def msg = nameValidate(categoryInstance);
		if(msg) {
			flash.message = msg;
			//render(view: "edit", model: [categoryInstance: categoryInstance])
			redirect(action:'list')
			return
		}

        if (!categoryInstance.save(flush: true)) {
           // render(view: "edit", model: [categoryInstance: categoryInstance])
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
      //  redirect(action: "show", id: categoryInstance.id)
		redirect(action:'list')
    }

    def delete(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        try {
            categoryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
           // redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
}

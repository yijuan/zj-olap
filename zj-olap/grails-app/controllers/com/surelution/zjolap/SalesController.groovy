package com.surelution.zjolap

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.dao.DataIntegrityViolationException

class SalesController {

    static allowedMethods = [save: "POST"]
	def springSecurityService
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		
		def user = springSecurityService.currentUser
		def loginBranch = user.branch
	/*	def searchClosure =  {
			if(loginBranch) {
					eq("branch",loginBranch)
			}
		}*/
		
		def branch
		def fullTime
		if(params['branch.id']) {
			branch = Branch.get(params['branch.id'])
		}
		if(params['fullTime.id']) {
			fullTime = SalesFullTime.get(params['fullTime.id'])
		}
		
		def sortBy = params.sort
		def ord = params.order == 'desc'?'desc':'asc'
		
		def c = Sales.createCriteria()
		def result = c.list(max:10, offset:params.offset){
			if(loginBranch) {
				eq("branch",loginBranch)
			}
			
			if(branch) {
				eq('branch', branch)
			}
			
			if(fullTime) {
				eq('fullTime', fullTime)
			}
			
			if(sortBy) {
				order(sortBy, ord)
			}
		}
		
        [salesInstanceList: result, salesInstanceTotal: result.totalCount]
    }
	
	def downloadExcel() {
		def head =[
			"姓名",
			"所属分公司",
			"是否专职"
		];
		def sales = Sales.list()

		HSSFWorkbook wb = new HSSFWorkbook();
		def sheet = wb.createSheet();
		
		def row= sheet.createRow(0);
		
		//设置头
		for(int i=0;i<head.size();i++) {
			 def cell= row.createCell(i);
			 cell.setCellValue(head.get(i));
		}
		
		def idx = 1;
		//设置主体内容
		sales.each {s ->
			def rowbody= sheet.createRow(idx);
			createCell(0,getStringValue(s?.name),rowbody);
			createCell(1,getStringValue(s?.branch?.name),rowbody);
			createCell(2,s?.fullTime?.name,rowbody);
			idx++;
		}
		
		//下载
		response.setContentType("application/vnd.ms-excel")
		response.setHeader("Content-disposition", "attachment;filename=file.xls")
		def os = response.outputStream;
		wb.write(os)
		os.flush()
		return
	}

    def create() {
        [salesInstance: new Sales(params)]
    }
	
	def nameValidate(salesInstance) {
		if(!salesInstance?.name) {
			return "名称不能为空";
		}
		/*def  br = Sales.findByName(salesInstance?.name)
		if(br) {
			return "名称不能重复";
		}*/
	}

    def save() {
        def salesInstance = new Sales(params)
		
		def msg = nameValidate(salesInstance);
		if(msg) {
			flash.message = msg;
			//render(view: "create", model: [salesInstance: salesInstance])
			redirect(action:'list')
			return
		}
		
		def user = springSecurityService.currentUser
		def loginBranch = user.branch
		if(loginBranch) {
			salesInstance.branch = loginBranch
			
		}
        if (!salesInstance.save(flush: true)) {
           // render(view: "create", model: [salesInstance: salesInstance])
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'sales.label', default: 'Sales'), salesInstance.id])
      //  redirect(action: "show", id: salesInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def salesInstance = Sales.get(id)
        if (!salesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'sales.label', default: 'Sales'), id])
            redirect(action: "list")
            return
        }

        [salesInstance: salesInstance]
    }

    def edit(Long id) {
        def salesInstance = Sales.get(id)
        if (!salesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'sales.label', default: 'Sales'), id])
            redirect(action: "list")
            return
        }

        [salesInstance: salesInstance]
    }

    def update(Long id, Long version) {
        def salesInstance = Sales.get(id)
		
		
        if (!salesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'sales.label', default: 'Sales'), id])
            redirect(action: "list",params:[max:params.max,offset:params.offset])
            return
        }

        if (version != null) {
            if (salesInstance.version > version) {
                salesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'sales.label', default: 'Sales')] as Object[],
                          "Another user has updated this Sales while you were editing")
               // render(view: "edit", model: [salesInstance: salesInstance])
				redirect(action:'list',params:[max:params.max,offset:params.offset])
                return
            }
        }

        salesInstance.properties = params
		
		def msg = nameValidate(salesInstance);
		if(msg) {
			flash.message = msg;
			//render(view: "edit", model: [salesInstance: salesInstance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
			return
		}

        if (!salesInstance.save(flush: true)) {
           // render(view: "edit", model: [salesInstance: salesInstance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'sales.label', default: 'Sales'), salesInstance.id])
        //redirect(action: "show", id: salesInstance.id)
		redirect(action:'list',params:[max:params.max,offset:params.offset])
    }

    def delete(Long id) {
        def salesInstance = Sales.get(id)
        if (!salesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'sales.label', default: 'Sales'), id])
            redirect(action: "list")
            return
        }

		
		def  salesOrder= SalesOrder.findAllBySalesAndIsVailAndStatus(salesInstance,true,"ABLE")
		
		if(salesOrder) {
			flash.message = "系统中已经存在该客户经理的订单信息，不能删除该客户经理！"
		//	redirect(action: "show", id: salesInstance.id)
			redirect(action:'list')
			return
		}
		
		
        try {
            salesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'sales.label', default: 'Sales'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'sales.label', default: 'Sales'), id])
           // redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }

	private def createCell(index,value,row) {
		def cell= row.createCell(index, HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}

	private def getStringValue(value) {
		if(value) {
			return value+"";
		}
		
		return "";
	}
}
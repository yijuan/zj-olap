package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class StockExcelUploadController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [excelUploadInstanceList: StockExcelUpload.list(params), excelUploadInstanceTotal: StockExcelUpload.count()]
    }

    def create() {
        [stockExcelUploadInstance: new StockExcelUpload(params)]
    }

    def save() {
        def stockExcelUploadInstance = new StockExcelUpload(params)
        if (!stockExcelUploadInstance.save(flush: true)) {
            render(view: "create", model: [stockExcelUploadInstance: stockExcelUploadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'stockExcelUpload.label', default: 'StockExcelUpload'), stockExcelUploadInstance.id])
        redirect(action: "show", id: stockExcelUploadInstance.id)
    }

    def show(Long id) {
        def stockExcelUploadInstance = StockExcelUpload.get(id)
        if (!stockExcelUploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stockExcelUpload.label', default: 'StockExcelUpload'), id])
            redirect(action: "list")
            return
        }

        [stockExcelUploadInstance: stockExcelUploadInstance]
    }

    def edit(Long id) {
        def stockExcelUploadInstance = StockExcelUpload.get(id)
        if (!stockExcelUploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stockExcelUpload.label', default: 'StockExcelUpload'), id])
            redirect(action: "list")
            return
        }

        [stockExcelUploadInstance: stockExcelUploadInstance]
    }

    def update(Long id, Long version) {
        def stockExcelUploadInstance = StockExcelUpload.get(id)
        if (!stockExcelUploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stockExcelUpload.label', default: 'StockExcelUpload'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (stockExcelUploadInstance.version > version) {
                stockExcelUploadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'stockExcelUpload.label', default: 'StockExcelUpload')] as Object[],
                          "Another user has updated this StockExcelUpload while you were editing")
                render(view: "edit", model: [stockExcelUploadInstance: stockExcelUploadInstance])
                return
            }
        }

        stockExcelUploadInstance.properties = params

        if (!stockExcelUploadInstance.save(flush: true)) {
            render(view: "edit", model: [stockExcelUploadInstance: stockExcelUploadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'stockExcelUpload.label', default: 'StockExcelUpload'), stockExcelUploadInstance.id])
        redirect(action: "show", id: stockExcelUploadInstance.id)
    }

    def delete(Long id) {
        def stockExcelUploadInstance = StockExcelUpload.get(id)
        if (!stockExcelUploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stockExcelUpload.label', default: 'StockExcelUpload'), id])
            redirect(action: "list")
            return
        }

		def fileName = stockExcelUploadInstance?.originalFileName;
        try {
			
			List<CustomerStock>  customerStocks = CustomerStock.findAllByUpload(stockExcelUploadInstance)
//			salesOrders.each{
//				//it.isVail = false
//				//it.optionValue = SalesOrder.OPTION_VALUE_DELETE
//				it.delete()
//			}
			
			List<CustomerStockLog>  customerStockLogs = new ArrayList<CustomerStockLog>();
			customerStocks.each{
				def csl =CustomerStockLog.findByUpdateFrom(it);
				if(csl) {
					customerStockLogs.add(csl);
				}
			}
			
			CustomerStockLog.deleteAll(customerStockLogs);
			CustomerStock.deleteAll(customerStocks)
			stockExcelUploadInstance.deleted = true
			stockExcelUploadInstance.save(flush: true)
			
			
			
            //stockExcelUploadInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [fileName, ""])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [fileName, ""])
           redirect(action: "list")
        }
    }
	
	
	def downFile(Long id) {
		def fileUpload= StockExcelUpload.get(id)
		def filePath = fileUpload .filePath;
		
		File file = new File(filePath)
		
		response.setContentType("application/msexcel")
		response.setHeader("Content-disposition", "attachment;filename=${fileUpload.originalFileName}")
		
		
		def os = response.outputStream
		os << file.bytes
		os.flush()
		return
		
		
	}
}

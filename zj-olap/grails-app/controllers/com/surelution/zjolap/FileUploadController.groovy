package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class FileUploadController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		
		
		def fromID = params.fromID
		def useOpter = params.useOpter
		
		def result = FileUpload.createCriteria().list(max:100, offset:params.offset) {
			if(fromID) {
				eq('fromID', Long.parseLong(fromID))
			}
			
			if(useOpter) {
				eq('useOpter', useOpter)
			}
		}
		
        [fileUploadInstanceList: result, fileUploadInstanceTotal: result.totalCount]
    }
	
	

    def create() {
        [fileUploadInstance: new FileUpload(params)]
    }

    def save() {
        def fileUploadInstance = new FileUpload(params)
		
		
		def file = request.getFile('file')

		def filePath = grailsApplication.config.file.importer.file.location
		def uuid = UUID.randomUUID().toString()
		def destPath = "${filePath}${uuid}"
		
		
		file.transferTo(new File(destPath))

		
		
		fileUploadInstance.fileName   = file.originalFilename
		fileUploadInstance.fileUUID =uuid
		fileUploadInstance.fileUrl = destPath
		fileUploadInstance.uploadDate = new Date()
		fileUploadInstance.save(flush: true)
		
		def p = [fromID:params.fromID,useOpter:params.useOpter]
		
        if (!fileUploadInstance.save(flush: true)) {
            render(view: "create", model: [fileUploadInstance: fileUploadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), fileUploadInstance.id])
        redirect(action: "list", params:p )
    }

    def show(Long id) {
        def fileUploadInstance = FileUpload.get(id)
        if (!fileUploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), id])
            redirect(action: "list")
            return
        }

        [fileUploadInstance: fileUploadInstance]
    }

    def edit(Long id) {
        def fileUploadInstance = FileUpload.get(id)
        if (!fileUploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), id])
            redirect(action: "list")
            return
        }

        [fileUploadInstance: fileUploadInstance]
    }

    def update(Long id, Long version) {
        def fileUploadInstance = FileUpload.get(id)
        if (!fileUploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (fileUploadInstance.version > version) {
                fileUploadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'fileUpload.label', default: 'FileUpload')] as Object[],
                          "Another user has updated this FileUpload while you were editing")
                render(view: "edit", model: [fileUploadInstance: fileUploadInstance])
                return
            }
        }

        fileUploadInstance.properties = params

        if (!fileUploadInstance.save(flush: true)) {
            render(view: "edit", model: [fileUploadInstance: fileUploadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), fileUploadInstance.id])
        redirect(action: "show", id: fileUploadInstance.id)
    }

    def delete(Long id) {
        def fileUploadInstance = FileUpload.get(id)
        if (!fileUploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), id])
            redirect(action: "list")
            return
        }

		def fromID = fileUploadInstance.fromID
		def useOpter = fileUploadInstance.useOpter
		
		def p = [fromID:fromID,useOpter:useOpter]
        try {
            fileUploadInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), id])
            redirect(action: "list" ,params:p)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), id])
            redirect(action: "show", id: id)
        }
    }
	
	
	def downFile(Long id) {
		def fileUpload= FileUpload.get(id)
		
		def path = grailsApplication.config.file.importer.file.location
		def uuid =fileUpload.fileUUID
		println "${path}/${uuid}"
		File file = new File("${path}/${uuid}")
		
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment;filename=${fileUpload.fileName}")
		
		
		def os = response.outputStream
		os << file.bytes
		os.flush()
		return
		
		
	}
}

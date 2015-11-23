package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(ExcelUploadController)
@Mock(ExcelUpload)
class ExcelUploadControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/excelUpload/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.excelUploadInstanceList.size() == 0
        assert model.excelUploadInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.excelUploadInstance != null
    }

    void testSave() {
        controller.save()

        assert model.excelUploadInstance != null
        assert view == '/excelUpload/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/excelUpload/show/1'
        assert controller.flash.message != null
        assert ExcelUpload.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/excelUpload/list'

        populateValidParams(params)
        def excelUpload = new ExcelUpload(params)

        assert excelUpload.save() != null

        params.id = excelUpload.id

        def model = controller.show()

        assert model.excelUploadInstance == excelUpload
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/excelUpload/list'

        populateValidParams(params)
        def excelUpload = new ExcelUpload(params)

        assert excelUpload.save() != null

        params.id = excelUpload.id

        def model = controller.edit()

        assert model.excelUploadInstance == excelUpload
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/excelUpload/list'

        response.reset()

        populateValidParams(params)
        def excelUpload = new ExcelUpload(params)

        assert excelUpload.save() != null

        // test invalid parameters in update
        params.id = excelUpload.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/excelUpload/edit"
        assert model.excelUploadInstance != null

        excelUpload.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/excelUpload/show/$excelUpload.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        excelUpload.clearErrors()

        populateValidParams(params)
        params.id = excelUpload.id
        params.version = -1
        controller.update()

        assert view == "/excelUpload/edit"
        assert model.excelUploadInstance != null
        assert model.excelUploadInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/excelUpload/list'

        response.reset()

        populateValidParams(params)
        def excelUpload = new ExcelUpload(params)

        assert excelUpload.save() != null
        assert ExcelUpload.count() == 1

        params.id = excelUpload.id

        controller.delete()

        assert ExcelUpload.count() == 0
        assert ExcelUpload.get(excelUpload.id) == null
        assert response.redirectedUrl == '/excelUpload/list'
    }
}

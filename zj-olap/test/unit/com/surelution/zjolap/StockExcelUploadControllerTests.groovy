package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(StockExcelUploadController)
@Mock(StockExcelUpload)
class StockExcelUploadControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/stockExcelUpload/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.stockExcelUploadInstanceList.size() == 0
        assert model.stockExcelUploadInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.stockExcelUploadInstance != null
    }

    void testSave() {
        controller.save()

        assert model.stockExcelUploadInstance != null
        assert view == '/stockExcelUpload/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/stockExcelUpload/show/1'
        assert controller.flash.message != null
        assert StockExcelUpload.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/stockExcelUpload/list'

        populateValidParams(params)
        def stockExcelUpload = new StockExcelUpload(params)

        assert stockExcelUpload.save() != null

        params.id = stockExcelUpload.id

        def model = controller.show()

        assert model.stockExcelUploadInstance == stockExcelUpload
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/stockExcelUpload/list'

        populateValidParams(params)
        def stockExcelUpload = new StockExcelUpload(params)

        assert stockExcelUpload.save() != null

        params.id = stockExcelUpload.id

        def model = controller.edit()

        assert model.stockExcelUploadInstance == stockExcelUpload
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/stockExcelUpload/list'

        response.reset()

        populateValidParams(params)
        def stockExcelUpload = new StockExcelUpload(params)

        assert stockExcelUpload.save() != null

        // test invalid parameters in update
        params.id = stockExcelUpload.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/stockExcelUpload/edit"
        assert model.stockExcelUploadInstance != null

        stockExcelUpload.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/stockExcelUpload/show/$stockExcelUpload.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        stockExcelUpload.clearErrors()

        populateValidParams(params)
        params.id = stockExcelUpload.id
        params.version = -1
        controller.update()

        assert view == "/stockExcelUpload/edit"
        assert model.stockExcelUploadInstance != null
        assert model.stockExcelUploadInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/stockExcelUpload/list'

        response.reset()

        populateValidParams(params)
        def stockExcelUpload = new StockExcelUpload(params)

        assert stockExcelUpload.save() != null
        assert StockExcelUpload.count() == 1

        params.id = stockExcelUpload.id

        controller.delete()

        assert StockExcelUpload.count() == 0
        assert StockExcelUpload.get(stockExcelUpload.id) == null
        assert response.redirectedUrl == '/stockExcelUpload/list'
    }
}

package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(InitCustomerStockController)
@Mock(InitCustomerStock)
class InitCustomerStockControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/initCustomerStock/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.initCustomerStockInstanceList.size() == 0
        assert model.initCustomerStockInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.initCustomerStockInstance != null
    }

    void testSave() {
        controller.save()

        assert model.initCustomerStockInstance != null
        assert view == '/initCustomerStock/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/initCustomerStock/show/1'
        assert controller.flash.message != null
        assert InitCustomerStock.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/initCustomerStock/list'

        populateValidParams(params)
        def initCustomerStock = new InitCustomerStock(params)

        assert initCustomerStock.save() != null

        params.id = initCustomerStock.id

        def model = controller.show()

        assert model.initCustomerStockInstance == initCustomerStock
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/initCustomerStock/list'

        populateValidParams(params)
        def initCustomerStock = new InitCustomerStock(params)

        assert initCustomerStock.save() != null

        params.id = initCustomerStock.id

        def model = controller.edit()

        assert model.initCustomerStockInstance == initCustomerStock
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/initCustomerStock/list'

        response.reset()

        populateValidParams(params)
        def initCustomerStock = new InitCustomerStock(params)

        assert initCustomerStock.save() != null

        // test invalid parameters in update
        params.id = initCustomerStock.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/initCustomerStock/edit"
        assert model.initCustomerStockInstance != null

        initCustomerStock.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/initCustomerStock/show/$initCustomerStock.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        initCustomerStock.clearErrors()

        populateValidParams(params)
        params.id = initCustomerStock.id
        params.version = -1
        controller.update()

        assert view == "/initCustomerStock/edit"
        assert model.initCustomerStockInstance != null
        assert model.initCustomerStockInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/initCustomerStock/list'

        response.reset()

        populateValidParams(params)
        def initCustomerStock = new InitCustomerStock(params)

        assert initCustomerStock.save() != null
        assert InitCustomerStock.count() == 1

        params.id = initCustomerStock.id

        controller.delete()

        assert InitCustomerStock.count() == 0
        assert InitCustomerStock.get(initCustomerStock.id) == null
        assert response.redirectedUrl == '/initCustomerStock/list'
    }
}

package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerStockController)
@Mock(CustomerStock)
class CustomerStockControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/customerStock/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.customerStockInstanceList.size() == 0
        assert model.customerStockInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.customerStockInstance != null
    }

    void testSave() {
        controller.save()

        assert model.customerStockInstance != null
        assert view == '/customerStock/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/customerStock/show/1'
        assert controller.flash.message != null
        assert CustomerStock.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/customerStock/list'

        populateValidParams(params)
        def customerStock = new CustomerStock(params)

        assert customerStock.save() != null

        params.id = customerStock.id

        def model = controller.show()

        assert model.customerStockInstance == customerStock
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/customerStock/list'

        populateValidParams(params)
        def customerStock = new CustomerStock(params)

        assert customerStock.save() != null

        params.id = customerStock.id

        def model = controller.edit()

        assert model.customerStockInstance == customerStock
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/customerStock/list'

        response.reset()

        populateValidParams(params)
        def customerStock = new CustomerStock(params)

        assert customerStock.save() != null

        // test invalid parameters in update
        params.id = customerStock.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/customerStock/edit"
        assert model.customerStockInstance != null

        customerStock.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/customerStock/show/$customerStock.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        customerStock.clearErrors()

        populateValidParams(params)
        params.id = customerStock.id
        params.version = -1
        controller.update()

        assert view == "/customerStock/edit"
        assert model.customerStockInstance != null
        assert model.customerStockInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customerStock/list'

        response.reset()

        populateValidParams(params)
        def customerStock = new CustomerStock(params)

        assert customerStock.save() != null
        assert CustomerStock.count() == 1

        params.id = customerStock.id

        controller.delete()

        assert CustomerStock.count() == 0
        assert CustomerStock.get(customerStock.id) == null
        assert response.redirectedUrl == '/customerStock/list'
    }
}

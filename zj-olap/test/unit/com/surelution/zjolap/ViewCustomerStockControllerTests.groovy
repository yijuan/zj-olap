package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(ViewCustomerStockController)
@Mock(ViewCustomerStock)
class ViewCustomerStockControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/viewCustomerStock/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.viewCustomerStockInstanceList.size() == 0
        assert model.viewCustomerStockInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.viewCustomerStockInstance != null
    }

    void testSave() {
        controller.save()

        assert model.viewCustomerStockInstance != null
        assert view == '/viewCustomerStock/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/viewCustomerStock/show/1'
        assert controller.flash.message != null
        assert ViewCustomerStock.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/viewCustomerStock/list'

        populateValidParams(params)
        def viewCustomerStock = new ViewCustomerStock(params)

        assert viewCustomerStock.save() != null

        params.id = viewCustomerStock.id

        def model = controller.show()

        assert model.viewCustomerStockInstance == viewCustomerStock
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/viewCustomerStock/list'

        populateValidParams(params)
        def viewCustomerStock = new ViewCustomerStock(params)

        assert viewCustomerStock.save() != null

        params.id = viewCustomerStock.id

        def model = controller.edit()

        assert model.viewCustomerStockInstance == viewCustomerStock
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/viewCustomerStock/list'

        response.reset()

        populateValidParams(params)
        def viewCustomerStock = new ViewCustomerStock(params)

        assert viewCustomerStock.save() != null

        // test invalid parameters in update
        params.id = viewCustomerStock.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/viewCustomerStock/edit"
        assert model.viewCustomerStockInstance != null

        viewCustomerStock.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/viewCustomerStock/show/$viewCustomerStock.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        viewCustomerStock.clearErrors()

        populateValidParams(params)
        params.id = viewCustomerStock.id
        params.version = -1
        controller.update()

        assert view == "/viewCustomerStock/edit"
        assert model.viewCustomerStockInstance != null
        assert model.viewCustomerStockInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/viewCustomerStock/list'

        response.reset()

        populateValidParams(params)
        def viewCustomerStock = new ViewCustomerStock(params)

        assert viewCustomerStock.save() != null
        assert ViewCustomerStock.count() == 1

        params.id = viewCustomerStock.id

        controller.delete()

        assert ViewCustomerStock.count() == 0
        assert ViewCustomerStock.get(viewCustomerStock.id) == null
        assert response.redirectedUrl == '/viewCustomerStock/list'
    }
}

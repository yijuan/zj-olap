package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(SalesOrderController)
@Mock(SalesOrder)
class SalesOrderControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/salesOrder/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.salesOrderInstanceList.size() == 0
        assert model.salesOrderInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.salesOrderInstance != null
    }

    void testSave() {
        controller.save()

        assert model.salesOrderInstance != null
        assert view == '/salesOrder/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/salesOrder/show/1'
        assert controller.flash.message != null
        assert SalesOrder.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/salesOrder/list'

        populateValidParams(params)
        def salesOrder = new SalesOrder(params)

        assert salesOrder.save() != null

        params.id = salesOrder.id

        def model = controller.show()

        assert model.salesOrderInstance == salesOrder
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/salesOrder/list'

        populateValidParams(params)
        def salesOrder = new SalesOrder(params)

        assert salesOrder.save() != null

        params.id = salesOrder.id

        def model = controller.edit()

        assert model.salesOrderInstance == salesOrder
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/salesOrder/list'

        response.reset()

        populateValidParams(params)
        def salesOrder = new SalesOrder(params)

        assert salesOrder.save() != null

        // test invalid parameters in update
        params.id = salesOrder.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/salesOrder/edit"
        assert model.salesOrderInstance != null

        salesOrder.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/salesOrder/show/$salesOrder.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        salesOrder.clearErrors()

        populateValidParams(params)
        params.id = salesOrder.id
        params.version = -1
        controller.update()

        assert view == "/salesOrder/edit"
        assert model.salesOrderInstance != null
        assert model.salesOrderInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/salesOrder/list'

        response.reset()

        populateValidParams(params)
        def salesOrder = new SalesOrder(params)

        assert salesOrder.save() != null
        assert SalesOrder.count() == 1

        params.id = salesOrder.id

        controller.delete()

        assert SalesOrder.count() == 0
        assert SalesOrder.get(salesOrder.id) == null
        assert response.redirectedUrl == '/salesOrder/list'
    }
}

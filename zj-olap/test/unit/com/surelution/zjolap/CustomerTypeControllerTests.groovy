package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerTypeController)
@Mock(CustomerType)
class CustomerTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/customerType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.customerTypeInstanceList.size() == 0
        assert model.customerTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.customerTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.customerTypeInstance != null
        assert view == '/customerType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/customerType/show/1'
        assert controller.flash.message != null
        assert CustomerType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/customerType/list'

        populateValidParams(params)
        def customerType = new CustomerType(params)

        assert customerType.save() != null

        params.id = customerType.id

        def model = controller.show()

        assert model.customerTypeInstance == customerType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/customerType/list'

        populateValidParams(params)
        def customerType = new CustomerType(params)

        assert customerType.save() != null

        params.id = customerType.id

        def model = controller.edit()

        assert model.customerTypeInstance == customerType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/customerType/list'

        response.reset()

        populateValidParams(params)
        def customerType = new CustomerType(params)

        assert customerType.save() != null

        // test invalid parameters in update
        params.id = customerType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/customerType/edit"
        assert model.customerTypeInstance != null

        customerType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/customerType/show/$customerType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        customerType.clearErrors()

        populateValidParams(params)
        params.id = customerType.id
        params.version = -1
        controller.update()

        assert view == "/customerType/edit"
        assert model.customerTypeInstance != null
        assert model.customerTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customerType/list'

        response.reset()

        populateValidParams(params)
        def customerType = new CustomerType(params)

        assert customerType.save() != null
        assert CustomerType.count() == 1

        params.id = customerType.id

        controller.delete()

        assert CustomerType.count() == 0
        assert CustomerType.get(customerType.id) == null
        assert response.redirectedUrl == '/customerType/list'
    }
}

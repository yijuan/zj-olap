package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerVistingTypeController)
@Mock(CustomerVistingType)
class CustomerVistingTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/customerVistingType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.customerVistingTypeInstanceList.size() == 0
        assert model.customerVistingTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.customerVistingTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.customerVistingTypeInstance != null
        assert view == '/customerVistingType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/customerVistingType/show/1'
        assert controller.flash.message != null
        assert CustomerVistingType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/customerVistingType/list'

        populateValidParams(params)
        def customerVistingType = new CustomerVistingType(params)

        assert customerVistingType.save() != null

        params.id = customerVistingType.id

        def model = controller.show()

        assert model.customerVistingTypeInstance == customerVistingType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/customerVistingType/list'

        populateValidParams(params)
        def customerVistingType = new CustomerVistingType(params)

        assert customerVistingType.save() != null

        params.id = customerVistingType.id

        def model = controller.edit()

        assert model.customerVistingTypeInstance == customerVistingType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/customerVistingType/list'

        response.reset()

        populateValidParams(params)
        def customerVistingType = new CustomerVistingType(params)

        assert customerVistingType.save() != null

        // test invalid parameters in update
        params.id = customerVistingType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/customerVistingType/edit"
        assert model.customerVistingTypeInstance != null

        customerVistingType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/customerVistingType/show/$customerVistingType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        customerVistingType.clearErrors()

        populateValidParams(params)
        params.id = customerVistingType.id
        params.version = -1
        controller.update()

        assert view == "/customerVistingType/edit"
        assert model.customerVistingTypeInstance != null
        assert model.customerVistingTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customerVistingType/list'

        response.reset()

        populateValidParams(params)
        def customerVistingType = new CustomerVistingType(params)

        assert customerVistingType.save() != null
        assert CustomerVistingType.count() == 1

        params.id = customerVistingType.id

        controller.delete()

        assert CustomerVistingType.count() == 0
        assert CustomerVistingType.get(customerVistingType.id) == null
        assert response.redirectedUrl == '/customerVistingType/list'
    }
}

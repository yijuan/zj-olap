package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerVistingController)
@Mock(CustomerVisting)
class CustomerVistingControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/customerVisting/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.customerVistingInstanceList.size() == 0
        assert model.customerVistingInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.customerVistingInstance != null
    }

    void testSave() {
        controller.save()

        assert model.customerVistingInstance != null
        assert view == '/customerVisting/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/customerVisting/show/1'
        assert controller.flash.message != null
        assert CustomerVisting.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/customerVisting/list'

        populateValidParams(params)
        def customerVisting = new CustomerVisting(params)

        assert customerVisting.save() != null

        params.id = customerVisting.id

        def model = controller.show()

        assert model.customerVistingInstance == customerVisting
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/customerVisting/list'

        populateValidParams(params)
        def customerVisting = new CustomerVisting(params)

        assert customerVisting.save() != null

        params.id = customerVisting.id

        def model = controller.edit()

        assert model.customerVistingInstance == customerVisting
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/customerVisting/list'

        response.reset()

        populateValidParams(params)
        def customerVisting = new CustomerVisting(params)

        assert customerVisting.save() != null

        // test invalid parameters in update
        params.id = customerVisting.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/customerVisting/edit"
        assert model.customerVistingInstance != null

        customerVisting.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/customerVisting/show/$customerVisting.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        customerVisting.clearErrors()

        populateValidParams(params)
        params.id = customerVisting.id
        params.version = -1
        controller.update()

        assert view == "/customerVisting/edit"
        assert model.customerVistingInstance != null
        assert model.customerVistingInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customerVisting/list'

        response.reset()

        populateValidParams(params)
        def customerVisting = new CustomerVisting(params)

        assert customerVisting.save() != null
        assert CustomerVisting.count() == 1

        params.id = customerVisting.id

        controller.delete()

        assert CustomerVisting.count() == 0
        assert CustomerVisting.get(customerVisting.id) == null
        assert response.redirectedUrl == '/customerVisting/list'
    }
}

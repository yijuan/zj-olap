package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerTypeLevel3Controller)
@Mock(CustomerTypeLevel3)
class CustomerTypeLevel3ControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/customerTypeLevel3/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.customerTypeLevel3InstanceList.size() == 0
        assert model.customerTypeLevel3InstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.customerTypeLevel3Instance != null
    }

    void testSave() {
        controller.save()

        assert model.customerTypeLevel3Instance != null
        assert view == '/customerTypeLevel3/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/customerTypeLevel3/show/1'
        assert controller.flash.message != null
        assert CustomerTypeLevel3.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/customerTypeLevel3/list'

        populateValidParams(params)
        def customerTypeLevel3 = new CustomerTypeLevel3(params)

        assert customerTypeLevel3.save() != null

        params.id = customerTypeLevel3.id

        def model = controller.show()

        assert model.customerTypeLevel3Instance == customerTypeLevel3
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/customerTypeLevel3/list'

        populateValidParams(params)
        def customerTypeLevel3 = new CustomerTypeLevel3(params)

        assert customerTypeLevel3.save() != null

        params.id = customerTypeLevel3.id

        def model = controller.edit()

        assert model.customerTypeLevel3Instance == customerTypeLevel3
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/customerTypeLevel3/list'

        response.reset()

        populateValidParams(params)
        def customerTypeLevel3 = new CustomerTypeLevel3(params)

        assert customerTypeLevel3.save() != null

        // test invalid parameters in update
        params.id = customerTypeLevel3.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/customerTypeLevel3/edit"
        assert model.customerTypeLevel3Instance != null

        customerTypeLevel3.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/customerTypeLevel3/show/$customerTypeLevel3.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        customerTypeLevel3.clearErrors()

        populateValidParams(params)
        params.id = customerTypeLevel3.id
        params.version = -1
        controller.update()

        assert view == "/customerTypeLevel3/edit"
        assert model.customerTypeLevel3Instance != null
        assert model.customerTypeLevel3Instance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customerTypeLevel3/list'

        response.reset()

        populateValidParams(params)
        def customerTypeLevel3 = new CustomerTypeLevel3(params)

        assert customerTypeLevel3.save() != null
        assert CustomerTypeLevel3.count() == 1

        params.id = customerTypeLevel3.id

        controller.delete()

        assert CustomerTypeLevel3.count() == 0
        assert CustomerTypeLevel3.get(customerTypeLevel3.id) == null
        assert response.redirectedUrl == '/customerTypeLevel3/list'
    }
}

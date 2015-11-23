package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(WarningRuleToBranchCustomerController)
@Mock(WarningRuleToBranchCustomer)
class WarningRuleToBranchCustomerControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/warningRuleToBranchCustomer/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.warningRuleToBranchCustomerInstanceList.size() == 0
        assert model.warningRuleToBranchCustomerInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.warningRuleToBranchCustomerInstance != null
    }

    void testSave() {
        controller.save()

        assert model.warningRuleToBranchCustomerInstance != null
        assert view == '/warningRuleToBranchCustomer/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/warningRuleToBranchCustomer/show/1'
        assert controller.flash.message != null
        assert WarningRuleToBranchCustomer.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/warningRuleToBranchCustomer/list'

        populateValidParams(params)
        def warningRuleToBranchCustomer = new WarningRuleToBranchCustomer(params)

        assert warningRuleToBranchCustomer.save() != null

        params.id = warningRuleToBranchCustomer.id

        def model = controller.show()

        assert model.warningRuleToBranchCustomerInstance == warningRuleToBranchCustomer
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/warningRuleToBranchCustomer/list'

        populateValidParams(params)
        def warningRuleToBranchCustomer = new WarningRuleToBranchCustomer(params)

        assert warningRuleToBranchCustomer.save() != null

        params.id = warningRuleToBranchCustomer.id

        def model = controller.edit()

        assert model.warningRuleToBranchCustomerInstance == warningRuleToBranchCustomer
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/warningRuleToBranchCustomer/list'

        response.reset()

        populateValidParams(params)
        def warningRuleToBranchCustomer = new WarningRuleToBranchCustomer(params)

        assert warningRuleToBranchCustomer.save() != null

        // test invalid parameters in update
        params.id = warningRuleToBranchCustomer.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/warningRuleToBranchCustomer/edit"
        assert model.warningRuleToBranchCustomerInstance != null

        warningRuleToBranchCustomer.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/warningRuleToBranchCustomer/show/$warningRuleToBranchCustomer.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        warningRuleToBranchCustomer.clearErrors()

        populateValidParams(params)
        params.id = warningRuleToBranchCustomer.id
        params.version = -1
        controller.update()

        assert view == "/warningRuleToBranchCustomer/edit"
        assert model.warningRuleToBranchCustomerInstance != null
        assert model.warningRuleToBranchCustomerInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/warningRuleToBranchCustomer/list'

        response.reset()

        populateValidParams(params)
        def warningRuleToBranchCustomer = new WarningRuleToBranchCustomer(params)

        assert warningRuleToBranchCustomer.save() != null
        assert WarningRuleToBranchCustomer.count() == 1

        params.id = warningRuleToBranchCustomer.id

        controller.delete()

        assert WarningRuleToBranchCustomer.count() == 0
        assert WarningRuleToBranchCustomer.get(warningRuleToBranchCustomer.id) == null
        assert response.redirectedUrl == '/warningRuleToBranchCustomer/list'
    }
}

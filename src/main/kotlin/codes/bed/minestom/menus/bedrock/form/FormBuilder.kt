package codes.bed.minestom.menus.bedrock.form


interface FormBuilder<B : FormBuilder<B, F>?, F : Form?> {
    fun title(title : String) : B?
    fun build() : F
}

